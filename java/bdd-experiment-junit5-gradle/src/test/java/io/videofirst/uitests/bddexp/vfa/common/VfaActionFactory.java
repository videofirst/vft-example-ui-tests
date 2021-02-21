package io.videofirst.uitests.bddexp.vfa.common;

import io.videofirst.uitests.bddexp.vfa.exceptions.VfaException;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiClick;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiExists;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiOpen;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiTextContains;
import io.videofirst.uitests.bddexp.vfa.modules.ui.actions.UiType;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class to create instances of VfaAction interfaces.
 *
 * @author Bob Marks
 */
public class VfaActionFactory {

    private static Map<String, VfaAction> actions = new HashMap<>();

    public VfaActionFactory() {
//        actions.put();
    }

    public static VfaAction create(String type, String... args) {
        if (args.length < 1) {
            throw new VfaException(
                "At least 1 arguments is required: - <type> <arg> (e.g. open /login)");
        }

        // TODO - use reflection
        VfaAction action = null;
        if (type.equals("open")) {
            action = new UiOpen();
        } else if (type.equals("type")) {
            action = new UiType();
        } else if (type.equals("click")) {
            action = new UiClick();
        } else if (type.equals("exists")) {
            action = new UiExists();
        } else if (type.equals("text_contains")) {
            action = new UiTextContains();
        }

        if (action != null) {
            action.init(args);
            return action;
        }

        // todo
        return null;
    }

}
