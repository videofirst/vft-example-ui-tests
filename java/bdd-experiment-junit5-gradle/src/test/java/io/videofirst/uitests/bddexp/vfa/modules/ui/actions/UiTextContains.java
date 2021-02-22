package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.AbstractUiAction;

/**
 * UI click action.
 *
 * @author Bob Marks
 */
public class UiTextContains extends AbstractUiAction {

    @Override
    public void init(String... args) {
        initTargetValue(args);
    }

    @Override
    public VfaResult execute() {
        elm(target).text().contains(value);
        return new VfaResult(target + " \"" + value + "\"");
    }

    @Override
    public String getCommand() {
        return "text_contains";
    }

}
