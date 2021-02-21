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
        initTarget(args);
    }

    @Override
    public VfaResult execute() {
        elm(target).click();
        return new VfaResult(target);
    }

    @Override
    public String getCommand() {
        return "text_contains";
    }

}
