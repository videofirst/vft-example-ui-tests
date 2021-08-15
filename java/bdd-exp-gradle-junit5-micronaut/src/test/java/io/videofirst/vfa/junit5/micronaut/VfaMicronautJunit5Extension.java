package io.videofirst.vfa.junit5.micronaut;

import io.micronaut.aop.InterceptedProxy;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.Qualifier;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.type.Argument;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import io.micronaut.inject.FieldInjectionPoint;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.test.annotation.MicronautTestValue;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.context.TestContext;
import io.micronaut.test.extensions.junit5.MicronautJunit5Extension;
import io.micronaut.test.support.TestPropertyProvider;
import io.videofirst.vfa.Feature;
import io.videofirst.vfa.Scenario;
import io.videofirst.vfa.exceptions.VfaException;
import io.videofirst.vfa.model.VfaFeature;
import io.videofirst.vfa.model.VfaScenario;
import io.videofirst.vfa.service.VfaService;
import io.videofirst.vfa.util.VfaUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestInstantiationException;
import org.junit.platform.commons.support.AnnotationSupport;

/**
 * Junit5 extension which uses Microanut Framework and Video First Automation annotations.
 * <p>
 */
public class VfaMicronautJunit5Extension extends MicronautJunit5Extension {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
        .create(VfaMicronautJunit5Extension.class);

    private VfaService vfaService;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        super.beforeAll(extensionContext);

        VfaService vfaService = applicationContext.getBean(VfaService.class);
        if (vfaService == null) {
            new VfaException("Service not initialised - exiting");
        }
        this.vfaService = vfaService;

        // TODO Refactor ???
        final Class<?> testClass = extensionContext.getRequiredTestClass();
        final Feature featureAnnotation = AnnotationSupport.findAnnotation(testClass, Feature.class).get();

        long id = featureAnnotation.id();
        String className = testClass.getName();
        String textFromClass = testClass.getSimpleName().replaceAll("(Feature|Test)$", "");
        textFromClass = VfaUtils.camelCaseToHumanReadable(textFromClass);
        String textFromAnnotation = featureAnnotation.text().trim();
        String text = !textFromAnnotation.isEmpty() ? textFromAnnotation : textFromClass;
        String description = featureAnnotation.description().trim();

        VfaFeature feature = VfaFeature.builder()
            .id(id)
            .text(text)
            .description(description)
            .className(className)
            .build();

        this.vfaService.before(feature);
    }

    /**
     * Builds a {@link MicronautTestValue} object from the provided class (e.g. by scanning annotations).
     *
     * @param testClass the class to extract builder configuration from
     * @return a MicronautTestValue to configure the test application context
     */
    protected MicronautTestValue buildMicronautTestValue(Class<?> testClass) {
        final Optional<Feature> featureAnnotation = AnnotationSupport.findAnnotation(testClass, Feature.class);
        return featureAnnotation
            .map(VfaMicronautJunit5Extension::buildValueObject)
            .orElseThrow(() -> new VfaException("Cannot run extension without Feature annotation present"));
    }

    /**
     * Copied from AnnotationUtils.
     */
    private static MicronautTestValue buildValueObject(Feature feature) {
        if (feature != null) {
            return new MicronautTestValue(
                feature.application(),
                feature.environments(),
                feature.packages(),
                feature.propertySources(),
                feature.rollback(),
                feature.transactional(),
                feature.rebuildContext(),
                feature.contextBuilder(),
                feature.transactionMode(),
                feature.startApplication());
        } else {
            return null;
        }
    }

    @Override
    public void interceptBeforeEachMethod(InvocationInterceptor.Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        beforeSetupTest(buildContext(extensionContext));
        invocation.proceed();
        afterSetupTest(buildContext(extensionContext));
    }

    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        beforeCleanupTest(buildContext(extensionContext));
        invocation.proceed();
        afterCleanupTest(buildContext(extensionContext));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        afterTestClass(buildContext(extensionContext));
        afterClass(extensionContext);

        VfaFeature feature = this.vfaService.getCurrentFeature();
        if (feature != null) {
            this.vfaService.after(feature);
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        injectEnclosingTestInstances(extensionContext);
        final Optional<Object> testInstance = extensionContext.getTestInstance();
        final Optional<Method> testMethod = extensionContext.getTestMethod();
        List<Property> propertyAnnotations = null;
        if (testMethod.isPresent()) {
            // What are these used for ??
            Property[] annotationsByType = testMethod.get().getAnnotationsByType(Property.class);
            propertyAnnotations = Arrays.asList(annotationsByType);
        }

        // =============================================================================================================
        // TODO Refactor
        if (testMethod.isPresent()) {
            final Scenario scenarioAnnotation = testMethod.get().getAnnotation(Scenario.class);

            if (scenarioAnnotation == null) {
                new VfaException("Cannot run test without Scenario annotation present");
            }

            long id = scenarioAnnotation.id();
            String methodName = testMethod.get().getName();
            String textFromMethod = methodName.replaceAll("_", " ");
            textFromMethod = VfaUtils.capFirst(textFromMethod);
            String textFromAnnotation = scenarioAnnotation.text().trim();
            String text = !textFromAnnotation.isEmpty() ? textFromAnnotation : textFromMethod;

            VfaScenario scenario = VfaScenario.builder()
                .id(id)
                .text(text)
                .methodName(methodName)
                .build();
            this.vfaService.before(scenario);
        }

        beforeEach(extensionContext, testInstance.orElse(null), testMethod.orElse(null), propertyAnnotations);
        beforeTestMethod(buildContext(extensionContext));
    }
    // =============================================================================================================

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        super.afterEach(extensionContext);

        VfaScenario scenario = this.vfaService.getCurrentScenario();
        if (scenario != null) {
            this.vfaService.after(scenario);
        }
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
        final Optional<Object> testInstance = extensionContext.getTestInstance();
        if (testInstance.isPresent()) {

            final Class<?> requiredTestClass = extensionContext.getRequiredTestClass();
            if (applicationContext.containsBean(requiredTestClass) || isNestedTestClass(requiredTestClass)) {
                return ConditionEvaluationResult.enabled("Test bean active");
            } else {

                final boolean hasBeanDefinition = isTestSuiteBeanPresent(requiredTestClass);
                if (!hasBeanDefinition) {
                    throw new TestInstantiationException(MISCONFIGURED_MESSAGE);
                } else {
                    return ConditionEvaluationResult.disabled(DISABLED_MESSAGE);
                }

            }
        } else {
            final Class<?> testClass = extensionContext.getRequiredTestClass();
            if (hasExpectedAnnotations(testClass) || isNestedTestClass(testClass)) {
                return ConditionEvaluationResult.enabled("Test bean active");
            } else {
                return ConditionEvaluationResult.disabled(DISABLED_MESSAGE);
            }
        }
    }

    /**
     * @param testClass the test class
     * @return true if the provided test class holds the expected test annotations
     */
    protected boolean hasExpectedAnnotations(Class<?> testClass) {
        return AnnotationSupport.isAnnotated(testClass, Feature.class);
    }

    @Override
    protected void resolveTestProperties(ExtensionContext context, MicronautTestValue testAnnotationValue,
        Map<String, Object> testProperties) {
        Object o = context.getTestInstance().orElse(null);
        if (o instanceof TestPropertyProvider) {
            Map<String, String> properties = ((TestPropertyProvider) o).getProperties();
            if (CollectionUtils.isNotEmpty(properties)) {
                testProperties.putAll(properties);
            }
        }
    }

    @Override
    protected void alignMocks(ExtensionContext context, Object instance) {
        if (specDefinition != null) {
            for (FieldInjectionPoint injectedField : specDefinition.getInjectedFields()) {
                final boolean isMock = applicationContext.resolveMetadata(injectedField.getType())
                    .isAnnotationPresent(MockBean.class);
                if (isMock) {
                    final Field field = injectedField.getField();
                    field.setAccessible(true);
                    try {
                        final Object mock = field.get(instance);
                        if (mock instanceof InterceptedProxy) {
                            InterceptedProxy ip = (InterceptedProxy) mock;
                            final Object target = ip.interceptedTarget();
                            field.set(instance, target);
                        }
                    } catch (IllegalAccessException e) {
                        // continue
                    }
                }
            }
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        afterTestExecution(buildContext(context));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        beforeTestExecution(buildContext(context));
    }

    private TestContext buildContext(ExtensionContext context) {
        return new TestContext(
            applicationContext,
            context.getTestClass().orElse(null),
            context.getTestMethod().orElse(null),
            context.getTestInstance().orElse(null),
            context.getExecutionException().orElse(null));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        final Argument<?> argument = getArgument(parameterContext, applicationContext);
        if (argument != null) {
            if (argument.isAnnotationPresent(Value.class) || argument.isAnnotationPresent(Property.class)) {
                return true;
            } else {
                return applicationContext.containsBean(argument.getType(), resolveQualifier(argument));
            }
        }
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        final Argument<?> argument = getArgument(parameterContext, applicationContext);
        if (argument != null) {
            Optional<String> v = argument.getAnnotationMetadata().stringValue(Value.class);
            if (v.isPresent()) {
                Optional<String> finalV = v;
                return applicationContext.getEnvironment().getProperty(v.get(), argument)
                    .orElseThrow(() ->
                        new ParameterResolutionException("Unresolvable property specified to @Value: " + finalV.get())
                    );
            } else {
                v = argument.getAnnotationMetadata().stringValue(Property.class, "name");
                if (v.isPresent()) {
                    Optional<String> finalV1 = v;
                    return applicationContext.getEnvironment()
                        .getProperty(v.get(), argument).orElseThrow(() ->
                            new ParameterResolutionException(
                                "Unresolvable property specified to @Property: " + finalV1.get())
                        );
                } else {
                    return applicationContext.getBean(argument.getType(), resolveQualifier(argument));
                }
            }
        } else {
            throw new ParameterResolutionException("Parameter cannot be resolved: " + parameterContext.getParameter());
        }
    }

    /**
     * @param context the current extension context
     * @return the store to use for this extension
     */
    protected ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getRoot().getStore(NAMESPACE);
    }

    private MicronautTestValue buildValueObject(
        io.micronaut.test.extensions.junit5.annotation.MicronautTest micronautTest) {
        return new MicronautTestValue(
            micronautTest.application(),
            micronautTest.environments(),
            micronautTest.packages(),
            micronautTest.propertySources(),
            micronautTest.rollback(),
            micronautTest.transactional(),
            micronautTest.rebuildContext(),
            micronautTest.contextBuilder(),
            micronautTest.transactionMode(),
            micronautTest.startApplication());
    }

    private boolean isNestedTestClass(Class<?> testClass) {
        return AnnotationSupport.isAnnotated(testClass, Nested.class);
    }

    private void injectEnclosingTestInstances(ExtensionContext extensionContext) {
        extensionContext.getTestInstances().ifPresent(testInstances -> {
            List<Object> allInstances = testInstances.getAllInstances();
            allInstances.stream().limit(allInstances.size() - 1).forEach(applicationContext::inject);
        });
    }

    private Argument<?> getArgument(ParameterContext parameterContext, ApplicationContext applicationContext) {
        try {
            final Executable declaringExecutable = parameterContext.getDeclaringExecutable();
            final int index = parameterContext.getIndex();
            if (declaringExecutable instanceof Constructor) {
                final Class<?> declaringClass = declaringExecutable.getDeclaringClass();
                final BeanDefinition<?> beanDefinition = applicationContext.findBeanDefinition(declaringClass)
                    .orElse(null);
                if (beanDefinition != null) {
                    final Argument<?>[] arguments = beanDefinition.getConstructor().getArguments();
                    if (index < arguments.length) {
                        return arguments[index];
                    }
                }
            } else {

                final ExecutableMethod<?, Object> executableMethod = applicationContext.getExecutableMethod(
                    declaringExecutable.getDeclaringClass(),
                    declaringExecutable.getName(),
                    declaringExecutable.getParameterTypes()
                );
                final Argument<?>[] arguments = executableMethod.getArguments();
                if (index < arguments.length) {
                    return arguments[index];
                }
            }
        } catch (NoSuchMethodException e) {
            return null;
        }
        return null;
    }


    /**
     * Build a qualifier for the given argument.
     *
     * @param argument The argument
     * @param <T> The type
     * @return The resolved qualifier
     */
    @SuppressWarnings("unchecked")
    private static <T> Qualifier<T> resolveQualifier(Argument<?> argument) {
        AnnotationMetadata annotationMetadata = Objects.requireNonNull(argument, "Argument cannot be null")
            .getAnnotationMetadata();
        boolean hasMetadata = annotationMetadata != AnnotationMetadata.EMPTY_METADATA;

        List<Class<? extends Annotation>> qualifierTypes =
            hasMetadata ? annotationMetadata.getAnnotationTypesByStereotype(javax.inject.Qualifier.class) : null;
        if (CollectionUtils.isNotEmpty(qualifierTypes)) {
            if (qualifierTypes.size() == 1) {
                return Qualifiers.byAnnotation(
                    annotationMetadata,
                    qualifierTypes.iterator().next()
                );
            } else {
                final Qualifier[] qualifiers = qualifierTypes
                    .stream().map((type) -> Qualifiers.byAnnotation(annotationMetadata, type))
                    .toArray(Qualifier[]::new);
                return Qualifiers.<T>byQualifiers(
                    qualifiers
                );
            }
        }
        return null;
    }

}