package io.videofirst.vfa.aop;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.enums.StepType;
import io.videofirst.vfa.exceptions.VfaException;
import io.videofirst.vfa.model.VfaStep;
import io.videofirst.vfa.service.VfaService;
import io.videofirst.vfa.util.VfaUtils;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VfaStepInterceptor implements MethodInterceptor<Object, Object> {

    private static final String METHOD_STEP_START_REGEX = "^(given|when|then|and|but)_";
    private static final Pattern METHOD_STEP_AND_TEXT_VALIDATOR = Pattern.compile(METHOD_STEP_START_REGEX + "\\w+");
    private static final boolean DEFAULT_ADD_QUOTES = true;

    @Inject
    private VfaService vfaService;

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {

        Method method = context.getTargetMethod();
        String methodName = context.getMethodName();

        StepType stepType = getStepType(method);
        String stepText = getStepText(method);
        boolean addQuotes = isAddQuotes(method);
        LinkedHashMap<String, Object> params = VfaUtils.getParamMapFromMethodContext(context);

        // Validate parameters before continuing
        validateStepTextParameters(stepText, methodName, params);

        VfaStep step = VfaStep.builder()
            .type(stepType)
            .text(stepText)
            .params(params)
            .addQuotes(addQuotes)
            .build();
        vfaService.before(step);

        Object object = context.proceed();

        vfaService.after(step);
        return object;
    }

    // Private methods

    private boolean isStepTypeFromMethod() {
        return vfaService.getCurrentScenario().getStepType() != null;
    }

    private StepType getStepType(Method method) {
        // Step type from a method e.g. given() get's precedence
        StepType stepTypeFromMethod = vfaService.getCurrentScenario().getStepType();
        if (stepTypeFromMethod != null) {
            return stepTypeFromMethod;
        } else {
            String methodName = method.getName();
            validateMethodName(methodName);
            return StepType.valueOf(methodName.substring(0, methodName.indexOf("_")));
        }
    }

    private String getStepText(Method method) {
        Step stepAnnotation = method.getAnnotation(Step.class);
        if (!stepAnnotation.value().trim().isEmpty()) {
            return stepAnnotation.value().trim();
        } else {
            String methodName = method.getName();
            validateMethodName(methodName);
            // ignore first part of method
            String stepText = methodName.replaceAll(METHOD_STEP_START_REGEX, "");
            return VfaUtils.underScoresToSentence(stepText, false);
        }
    }

    private void validateStepTextParameters(String stepText, String methodName, LinkedHashMap<String, Object> params) {
        // Count the number of parameters and ensure that it's not greater than the list of parameter map
        long paramCount = VfaUtils.countParameters(stepText);
        if (paramCount > params.size()) {
            throw new VfaException("Not enough parameters - step text [ " + stepText + " ] has [ " + paramCount +
                " ] but method [ " + methodName + " only has [ " + params.size() + " ] parameters");
        }
    }

    private void validateMethodName(String methodName) {
        // only check method name is the step type has not been set via a step type method e.g. given()
        if (!isStepTypeFromMethod()) {
            // If stepType isn't set then ensure method name starts with e.g. "given_"
            Matcher matcher = METHOD_STEP_AND_TEXT_VALIDATOR.matcher(methodName);
            if (!matcher.find()) {
                throw new RuntimeException(
                    "Invalid method [ " + methodName
                        + " ] - must start with  [ given, when, then, but, and ], " +
                        "followed with an underscore and at least one character");
            }
        }
    }

    private boolean isAddQuotes(Method method) {
        Step stepAnnotation = method.getAnnotation(Step.class);
        if (stepAnnotation != null) {
            return stepAnnotation.addQuotes();
        }
        return DEFAULT_ADD_QUOTES;
    }

}