package io.videofirst.google;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.videofirst.vfa.Action;
import io.videofirst.vfa.exceptions.VfaException;
import io.videofirst.vfa.exceptions.handlers.ThrowableConverter;
import javax.inject.Singleton;

/**
 * @author Bob Marks
 */
@Singleton
public class ErrorActions implements ThrowableConverter {

    //    {
//        Logger.getGlobal().setLevel(Level.OFF);
//    }
//
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

    @Override
    public Exception convertThrowable(Throwable throwable) {
        return new VfaException(throwable.getMessage(), throwable);
    }

}
