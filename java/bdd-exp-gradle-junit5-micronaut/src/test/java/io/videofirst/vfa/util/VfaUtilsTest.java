package io.videofirst.vfa.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit test to test the methods of VfaUtils.
 */
public class VfaUtilsTest {

    @Test
    public void shouldRepeat() {
        assertThat(VfaUtils.repeat(" ", 3)).isEqualTo("   ");
        assertThat(VfaUtils.repeat("*", 2)).isEqualTo("**");
        assertThat(VfaUtils.repeat("*", 4)).isEqualTo("****");
        assertThat(VfaUtils.repeat("dave", 3)).isEqualTo("davedavedave");

        // null / empty
        assertThat(VfaUtils.repeat("*", 0)).isNull();
        assertThat(VfaUtils.repeat("*", -1)).isNull();
        assertThat(VfaUtils.repeat("", 23)).isNull();
        assertThat(VfaUtils.repeat("", 0)).isNull();
        assertThat(VfaUtils.repeat(null, 23)).isNull();
    }

    @Test
    public void shouldCapFirst() {
        assertThat(VfaUtils.capFirst("t")).isEqualTo("T");
        assertThat(VfaUtils.capFirst(" t ")).isEqualTo(" t ");
        assertThat(VfaUtils.capFirst("Dave")).isEqualTo("Dave");

        // null / empty
        assertThat(VfaUtils.capFirst("")).isEqualTo("");
        assertThat(VfaUtils.capFirst(null)).isNull();
    }

    @Test
    public void shouldCamelCaseToHumanReadable() {
        assertThat(VfaUtils.camelCaseToHumanReadable("lowercase")).isEqualTo("lowercase");
        assertThat(VfaUtils.camelCaseToHumanReadable("Class")).isEqualTo("Class");
        assertThat(VfaUtils.camelCaseToHumanReadable("MyClass")).isEqualTo("My Class");
        assertThat(VfaUtils.camelCaseToHumanReadable("lowercase")).isEqualTo("lowercase");

        // null / empty
        assertThat(VfaUtils.camelCaseToHumanReadable("")).isEqualTo("");
        assertThat(VfaUtils.camelCaseToHumanReadable(null)).isNull();
    }

}