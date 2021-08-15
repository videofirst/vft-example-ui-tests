package io.videofirst.vfa.enums;

import static io.videofirst.vfa.util.VfaUtils.capFirst;
import static java.lang.String.format;

/**
 * Types of step i.e. Given, When, Then, And and But.
 */
public enum StepType {

    given, when, then, and, but, none;

    private final int longest = 5;

    /**
     * Convert type to a label i.e. capitalise the first letter and right align.
     */
    public String label() {
        String titledStep = capFirst(this.toString());
        return format("%5s", titledStep);
    }

}