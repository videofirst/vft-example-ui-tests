package io.videofirst.vfa.util;

import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.MutableArgumentValue;
import java.util.LinkedHashMap;
import org.apache.commons.lang3.StringUtils;

public class VfaUtils {

    // Constants

    public static final char PARAM_CHAR = '$';

    private static final String PARAM_DOUBLE_CHAR_SEARCH = "\\$\\$";
    private static final String PARAM_DOUBLE_CHAR_REPLACE = " \\$";

    // String utils

    /**
     * Repeat characters.
     */
    public static String repeat(String text, int length) {
        if (text == null || text.isEmpty() || length < 1) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(text);
        }
        return sb.toString();
    }

    /**
     * Capitalize an input String.
     */
    public static String capFirst(String input) {
        return input != null ? StringUtils.capitalize(input) : null;
    }

    /**
     * Convert a string from pascal case to title case.
     * <p>
     * Based on https://stackoverflow.com/a/18887130
     */
    public static String camelCaseToTitleCase(String input) {
        return input != null ? StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(input), ' '
        ) : null;
    }

    /**
     * Convert a string from underscore case to a sentence (makes it more readable).
     */
    public static String underScoresToSentence(String input, boolean capFirst) {
        if (input == null) {
            return null;
        }
        String sentence = input.replaceAll("_", " ");
        return capFirst ? capFirst(sentence) : sentence;
    }

    /**
     * Quote a String input.
     */
    public static String quote(String input) {
        input = input.replaceAll("\"", "\\\"");
        return "\"" + input + "\"";
    }

    // Parameter utils

    /**
     * Return a LinkedHashMap (natural order) of String/Objects from a method context.
     */
    public static LinkedHashMap<String, Object> getParamMapFromMethodContext(
        MethodInvocationContext<Object, Object> context) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        for (MutableArgumentValue param : context.getParameters().values()) {
            String paramName = param.getName();
            Object paramValue = param.getValue();
            params.put(paramName, paramValue);
        }
        return params;
    }

    /**
     * Return true of false if a String contains a parameter e.g. "I type the name $ in" will return true.
     */
    public static boolean containsParameters(String input) {
        if (input != null && input.indexOf(PARAM_CHAR) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Remove parameter escape characters from a String e.g. "The price $$" will return "The price  $"
     */
    public static String removeParameterEscapeCharacters(String stepText) {
        if (stepText == null) {
            return null;
        }
        return stepText.replaceAll(PARAM_DOUBLE_CHAR_SEARCH, PARAM_DOUBLE_CHAR_REPLACE);
    }

    /**
     * Count the number of parameters in a String. Note, we remove the escape parameter characters first before then
     * counting these characters.
     */
    public static long countParameters(String input) {
        if (!containsParameters(input)) {
            return 0;
        }
        String inputWithEscapeCharsRemoved = removeParameterEscapeCharacters(input);
        return inputWithEscapeCharsRemoved.chars().filter(c -> c == PARAM_CHAR).count();
    }

}