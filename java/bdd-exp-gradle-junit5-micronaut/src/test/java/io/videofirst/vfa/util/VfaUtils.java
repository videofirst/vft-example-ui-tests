package io.videofirst.vfa.util;

import org.apache.commons.lang3.StringUtils;

public class VfaUtils {

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
     * Convert a string from pascal case to a sentence (makes it more readable).
     * <p>
     * Based on https://stackoverflow.com/a/18887130
     */
    public static String camelCaseToHumanReadable(String input) {
        return input != null ? StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(input), ' '
        ) : null;
    }

    /**
     * Quote a String input.
     */
    public static String quote(String input) {
        input = input.replaceAll("\"", "\\\"");
        return "\"" + input + "\"";
    }

}