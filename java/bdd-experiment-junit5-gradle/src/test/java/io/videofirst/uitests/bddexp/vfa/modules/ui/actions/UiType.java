package io.videofirst.uitests.bddexp.vfa.modules.ui.actions;

import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import io.videofirst.uitests.bddexp.vfa.modules.ui.UiAction;

/**
 * UI type action.
 *
 * @author Bob Marks
 */
public class UiType extends UiAction {

    public static UiType type(String target, String value) {
        return new UiType().init(target, value);
    }

    @Override
    public UiType init(String... args) {
        initTargetValue(args);
        return this;
    }

    @Override
    public VfaResult execute() {
        elm(target).setValue(value);
        return new VfaResult(quote(target) + " " + quote(value)); // FIXME - DOES FEEL RIGHT HERE
    }

    @Override
    public String getCommand() {
        return "type";
    }

}
