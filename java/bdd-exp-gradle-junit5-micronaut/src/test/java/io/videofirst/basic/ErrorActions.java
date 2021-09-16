package io.videofirst.basic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.videofirst.vfa.Action;
import javax.inject.Singleton;

/**
 * @author Bob Marks
 */
@Singleton
public class ErrorActions {

    @Action
    public void error() {
        throw new RuntimeException("action error");
    }

    @Action
    public void fail() {
        assertTrue(false);
    }

    @Action
    public void success1() {
        assertTrue(true);
    }

    @Action
    public void success2(String input) {
        assertTrue(true);
    }

}
