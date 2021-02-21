package io.videofirst.uitests.bddexp.vfa.exceptions;

/**
 * VFA exception.
 *
 * @author Bob Marks
 */
public class VfaException extends RuntimeException {

    public VfaException(String message, Throwable cause) {
        super(message, cause);
    }

    public VfaException(String message) {
        super(message);
    }

    public VfaException(Throwable cause) {
        super(cause);
    }

}
