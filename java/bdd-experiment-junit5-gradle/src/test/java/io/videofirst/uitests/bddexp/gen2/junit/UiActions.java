package io.videofirst.uitests.bddexp.gen2.junit;

import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiAssertList;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiAssertValue;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiClick;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiExists;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiOpen;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiTextContains;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiType;

/**
 * Low-level UI actions.
 *
 * @author Bob Marks
 */
public class UiActions {

    public UiAssertList assert_list(String target, UiAssertList.Match match, String value) {
        return UiAssertList.assert_list(target, match, value);
    }

    public UiAssertValue assert_value(String target, String value) {
        return UiAssertValue.assert_value(target, value);
    }

    public UiClick click(String target) {
        return UiClick.click(target);
    }

    public UiExists exists(String target) {
        return UiExists.exists(target);
    }

    public UiOpen open(String target) {
        return UiOpen.open(target);
    }

    public UiTextContains text_contains(String target, String value) {
        return UiTextContains.text_contains(target, value);
    }

    public UiType type(String target, String value) {
        return UiType.type(target, value);
    }

}
