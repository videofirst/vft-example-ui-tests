package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI text contains action.
 *
 * @author Bob Marks
 */
public class UiTextContains extends UiAction {

    /**
     * Create a text_contains from a target and value.
     */
    public static UiTextContains text_contains(String target, String value) {
        return new UiTextContains().init(target, value);
    }

    @Override
    public UiTextContains init(String... args) {
        initTargetValue(args);
        return this;
    }

    @Override
    public VfaResult execute() {
        String elmText = elm(target).text();
        assertThat(elmText).contains(value).withFailMessage(
            "Expecting element [ " + target + " ] with text [ " + elmText + " ] to contain [ " + value + " ]");
        return new VfaResult(quote(target) + " " + quote(value)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "text_contains";
    }

}
