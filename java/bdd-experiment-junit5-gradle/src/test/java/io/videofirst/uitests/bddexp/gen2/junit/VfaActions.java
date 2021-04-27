package io.videofirst.uitests.bddexp.gen2.junit;

import static java.util.Arrays.asList;

import io.videofirst.uitests.bddexp.vfa.common.VfaAction;
import io.videofirst.uitests.bddexp.vfa.common.VfaResult;
import java.util.List;

/**
 * One of more actions, along with a name.
 *
 * @author Bob Marks
 */
public class VfaActions implements VfaAction {

    private final String command;
    private final List<VfaAction> actions;

    /**
     * Private constructor - can only be created via the init methods below.
     */
    private VfaActions(String command, List<VfaAction> actions) {
        this.command = command;
        this.actions = actions;
    }

    /**
     * Init using a command and a list of actions.
     */
    public static VfaActions actions(String command, VfaAction... actions) {
        return new VfaActions(command, asList(actions));
    }

    /**
     * Init using a command a list of Strings.
     */
    public static VfaActions actions(String command, String... actions) {
        return new VfaActions(command, parse(actions));
    }

    // Private methods

    private static List<VfaAction> parse(String... actions) {
        // TODO
        return null;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public VfaAction init(String... args) {
        return null; // ?????
    }

    @Override
    public VfaResult execute() {
        return new VfaResult(""); // return a list of results?
    }

}
