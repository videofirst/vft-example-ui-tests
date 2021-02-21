package io.videofirst.uitests.bddexp.vfa.common;

/**
 * VFA actions.
 *
 * @author Bob Marks
 */
public interface VfaAction {

    void init(String... args);

    String getCommand();

    VfaResult execute();

}
