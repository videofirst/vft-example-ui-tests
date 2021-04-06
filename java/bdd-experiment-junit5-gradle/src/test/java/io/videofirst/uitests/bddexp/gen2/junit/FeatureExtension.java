package io.videofirst.uitests.bddexp.gen2.junit;

import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

import io.videofirst.uitests.bddexp.vfa.VfaFeature;
import io.videofirst.uitests.bddexp.vfa.VfaScenario;
import io.videofirst.uitests.bddexp.vfa.VfaTestRunner;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Feature extension.  - rename ????
 *
 * @author Bob Marks
 */
public class FeatureExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback,
    ParameterResolver {

    private static final Namespace NAMESPACE = Namespace.create(FeatureExtension.class);

    private VfaTestRunner vfaTestRunner = new VfaTestRunner(); // key class

    public static ThreadLocal<FeatureClass> currentFeature = new ThreadLocal<>();
    public static ThreadLocal<ScenarioMethod> currentScenario = new ThreadLocal<>();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        if (!isFeature(context)) {
            throw new Exception("Use @Feature annotation to use FeatureExtension. Class: "
                + context.getRequiredTestClass());
        }

        Class<?> clazz = context.getRequiredTestClass();
        Feature feature = clazz.getAnnotation(Feature.class);
        VfaFeature vfaFeature = VfaFeature.builder()
            .id(feature.id())
            .name(feature.name())
            .description(feature.description())
            .build();

        FeatureClass featureClass = FeatureClass.builder()
            .vfaFeature(vfaFeature)
            .featureClass(clazz)
            .build();
        context.getStore(NAMESPACE).put(clazz.getName(), featureClass);

        currentFeature.set(featureClass);

        vfaTestRunner.logFeature(vfaFeature);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (!isFeature(context)) {
            return;
        }

        //new FeatureWriter(getStoryDetails(context)).write();           // TODO
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        if (!isScenario(context)) {
            throw new Exception(
                "Use @Scenario annotation to use the StoryExtension service. Method: "
                    + context.getRequiredTestMethod());
        }

        // Prepare a scene instance corresponding to the given test method.
        Scenario scenario = context.getRequiredTestMethod().getAnnotation(Scenario.class);
        VfaScenario vfaScenario = VfaScenario.builder()
            .id(scenario.id())
            .name(scenario.name())
            .build();
        ScenarioMethod scenarioMethod = ScenarioMethod.builder()
            .vfaScenario(vfaScenario)
            .methodName(context.getRequiredTestMethod().getName())
            .build();

        // is this needed
        //getFeatureClass(context).getStore().put(scenarioMethod.getMethodName(), scenarioMethod);

        currentScenario.set(scenarioMethod);

        vfaTestRunner.logScenario(vfaScenario);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
        ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();

        //return Scene.class.equals(parameter.getType());
        return true;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext context) {

        // Inject the previously created scene object into the test method.
        //return getStoryDetails(context).getStore().get(context.getRequiredTestMethod().getName());
        return true;
    }

    private static FeatureClass getFeatureClass(ExtensionContext context) {
        Class<?> clazz = context.getRequiredTestClass();
        return context.getStore(NAMESPACE).get(clazz.getName(), FeatureClass.class);
    }

    private static boolean isFeature(ExtensionContext context) {
        return isAnnotated(context.getRequiredTestClass(), Feature.class);
    }

    private static boolean isScenario(ExtensionContext context) {
        return isAnnotated(context.getRequiredTestMethod(), Scenario.class);
    }

}
