package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import static com.codeborne.selenide.Selenide.open;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.AbstractUiAction;

/**
 * UI Open action.
 *
 * @author Bob Marks
 */
public class UiOpen extends AbstractUiAction {

    @Override
    public void init(String... args) {
        initTarget(args);
    }

    @Override
    public VfaResult execute() {
        open(target);
        return new VfaResult(target);
    }

    @Override
    public String getCommand() {
        return "open";
    }

}
