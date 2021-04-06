package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import com.codeborne.selenide.Selenide;
import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI Open action.
 *
 * @author Bob Marks
 */
public class UiOpen extends UiAction {

    /**
     * Create an UiOpen from a target.
     */
    public static UiOpen open(String target) {
        return new UiOpen().init(target);
    }

    @Override
    public UiOpen init(String... args) {
        initTarget(args);
        return this;
    }

    @Override
    public VfaResult execute() {
        Selenide.open(target);
        return new VfaResult(quote(target)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "open";
    }

}
