package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI click action.
 *
 * @author Bob Marks
 */
public class UiClick extends UiAction {

    /**
     * Static method to create an UiClick instance from a target.
     */
    public static UiClick click(String target) {
        return new UiClick().init(target);
    }

    @Override
    public UiClick init(String... args) {
        initTarget(args);
        return this;
    }

    @Override
    public VfaResult execute() {
        elm(target).click();
        return new VfaResult(quote(target)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "click";
    }

}
