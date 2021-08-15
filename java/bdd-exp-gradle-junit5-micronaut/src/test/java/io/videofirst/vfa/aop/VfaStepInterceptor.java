package io.videofirst.vfa.aop;


import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.videofirst.vfa.Step;
import io.videofirst.vfa.enums.StepType;
import io.videofirst.vfa.exceptions.VfaException;
import io.videofirst.vfa.model.VfaStep;
import io.videofirst.vfa.service.VfaService;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VfaStepInterceptor implements MethodInterceptor<Object, Object> {

    private static final String METHOD_STEP_START_REGEX = "^(given|when|then|and|but)_";
    private static final Pattern METHOD_STEP_AND_TEXT_VALIDATOR = Pattern.compile(METHOD_STEP_START_REGEX + "\\w+");
    private static final char PARAM_CHAR = '$';
    private static final String PARAM_DOUBLE_CHAR_SEARCH = "\\$\\$";
    private static final String PARAM_DOUBLE_CHAR_REPLACE = "_\\$";

    @Inject
    private VfaService vfaService;

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {

        Method method = context.getTargetMethod();

        StepType stepType = getStepType(method);
        String stepText = getStepText(context, method);

        VfaStep step = VfaStep.builder()
            .type(stepType)
            .text(stepText)
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

    private String getStepText(MethodInvocationContext<Object, Object> context, Method method) {
        String stepText = extractStepText(method);
        if (stepText.indexOf(PARAM_CHAR) != -1) {
            stepText = updateStepTextWithParams(stepText, context);
        }
        return stepText;
    }

    private String extractStepText(Method method) {
        Step stepAnnotation = method.getAnnotation(Step.class);
        if (!stepAnnotation.value().trim().isEmpty()) {
            return stepAnnotation.value().trim();
        } else if (!stepAnnotation.text().trim().isEmpty()) {
            return stepAnnotation.text().trim();
        } else {
            String methodName = method.getName();
            validateMethodName(methodName);
            // ignore first part of method
            String stepText = methodName.replaceAll(METHOD_STEP_START_REGEX, "");
            return stepText.replaceAll("_", " "); // replace underscores with spaces
        }
    }

    private String updateStepTextWithParams(String stepText, MethodInvocationContext<Object, Object> context) {
        // First of all replace double instances of param char with just one (so preceding characters aren't included)
        String replacedStepText = stepText.replaceAll(PARAM_DOUBLE_CHAR_SEARCH, PARAM_DOUBLE_CHAR_REPLACE);
        long paramCount = replacedStepText.chars().filter(c -> c == PARAM_CHAR).count();
        List<Object> paramValues = context.getParameters().values().stream()
            .map(p -> p.getValue())
            .collect(Collectors.toList());

        // Validate we have enough parameters
        if (paramCount > paramValues.size()) {
            throw new VfaException("Not enough parameters - step text [ " + stepText + " ] has [ " + paramCount +
                " ] but method [ " + context.getMethodName() + " only has [ " + paramValues.size() + " ] parameters");
        }

        // Based loosely on [ https://stackoverflow.com/a/5034592 ]
        int index = replacedStepText.indexOf(PARAM_CHAR);
        int lastIndex = 0, paramIndex = 0;
        StringBuilder stepTextWithParams = new StringBuilder();
        while (index >= 0) {
            stepTextWithParams.append(stepText.substring(lastIndex, index));
            stepTextWithParams.append(paramValues.get(paramIndex++));
            lastIndex = index + 1;
            index = replacedStepText.indexOf("$", index + 1);
        }
        // Add remaining text (if exists)
        if (lastIndex < replacedStepText.length()) {
            stepTextWithParams.append(replacedStepText.substring(lastIndex));
        }
        return stepTextWithParams.toString();
    }

    private void validateMethodName(String methodName) {
        // only check method name is the step type has not been set via a step type method e.g. given()
        if (!isStepTypeFromMethod()) {
            // If stepType isn't set then ensure method name starts with e.g. "given_"
            Matcher matcher = METHOD_STEP_AND_TEXT_VALIDATOR.matcher(methodName);
            if (!matcher.find()) {
                throw new RuntimeException(
                    "Invalid method name - it must start with one of [ given, when, then, but, and ], " +
                        "then followed with an underscore and at least one character");
            }
        }
    }

}