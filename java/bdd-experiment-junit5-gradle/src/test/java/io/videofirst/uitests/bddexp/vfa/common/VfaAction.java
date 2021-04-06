package io.videofirst.uitests.bddexp.vfa.common;

/**
 * VFA action interface.
 *
 * @author Bob Marks
 */
public interface VfaAction<Action extends VfaAction<Action>> {

    Action init(String... args);

    String getCommand();

    VfaResult execute();

    /**
     * Add quotes if the input has any spaces.
     */
    default String quote(String input) {
        return input != null && input.contains(" ") ? "\"" + input + "\"" : input;
    }

}
