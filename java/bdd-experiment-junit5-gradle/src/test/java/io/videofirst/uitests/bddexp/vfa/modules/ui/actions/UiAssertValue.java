package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI assert value of element.
 *
 * @author Bob Marks
 */
public class UiAssertValue extends UiAction {

    /**
     * Create a text_contains from a target and value.
     */
    public static UiAssertValue assert_value(String target, String value) {
        return new UiAssertValue().init(target, value);
    }

    @Override
    public UiAssertValue init(String... args) {
        initTargetValue(args);
        return this;
    }

    @Override
    public VfaResult execute() {
        String elmValue = elm(target).val();
        assertThat(elmValue).isEqualTo(value).withFailMessage(
            "Expecting element [ " + target + " ] with value [ " + value + " ] to be equal to [ " + value + " ]");
        return new VfaResult(quote(target) + " " + quote(value)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "assert_value";
    }

}
