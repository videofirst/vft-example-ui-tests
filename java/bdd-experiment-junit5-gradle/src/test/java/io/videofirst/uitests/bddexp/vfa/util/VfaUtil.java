package io.videofirst.uitests.bddexp.vfa.util;

/**
 * Collection of useful static methods.
 *
 * @author Bob Marks
 */
public class VfaUtil {

    /**
     * Pad left a String.
     */
    public static String padLeft(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append(' ');
        }
        sb.append(inputString);
        return sb.toString();
    }

    public static String pad(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(' ');
        }
        return sb.toString();
    }

}
