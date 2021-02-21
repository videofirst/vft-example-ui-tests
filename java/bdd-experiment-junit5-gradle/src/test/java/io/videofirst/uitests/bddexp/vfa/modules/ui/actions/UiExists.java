package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import com.codeborne.selenide.Condition;
import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.AbstractUiAction;

/**
 * UI click action.
 *
 * @author Bob Marks
 */
public class UiExists extends AbstractUiAction {

    @Override
    public void init(String... args) {
        initTarget(args);
    }

    @Override
    public VfaResult execute() {
        elm(target).should(Condition.exist);
        return new VfaResult(target);
    }

    @Override
    public String getCommand() {
        return "exists";
    }

}
