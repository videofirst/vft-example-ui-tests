package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import com.codeborne.selenide.Condition;
import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI click action.
 *
 * @author Bob Marks
 */
public class UiExists extends UiAction {

    /**
     * Create an UiExists from a target.
     */
    public static UiExists exists(String target) {
        return new UiExists().init(target);
    }

    @Override
    public UiExists init(String... args) {
        initTarget(args);
        return this;
    }

    @Override
    public VfaResult execute() {
        elm(target).should(Condition.exist);
        return new VfaResult(quote(target)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "exists";
    }

}
