package io.videofirst.vfa.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author Bob Marks
 */
public class VfaSilentException extends RuntimeException {

    public VfaSilentException(Throwable throwable) {
        super(throwable);
    }

    public VfaSilentException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public VfaSilentException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        //System.out.println(" --- printStackTrace start ---");
        //super.printStackTrace();
        //System.out.println(" --- printStackTrace end ---");
    }

    @Override
    public void printStackTrace(PrintStream ps) {
        //System.out.println(" --- printStackTrace ps start ---");
        //super.printStackTrace(ps);
        //System.out.println(" --- printStackTrace ps end ---");
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
        //System.out.println(" --- printStackTrace pw start ---");
        //super.printStackTrace(pw);
        //System.out.println(" --- printStackTrace pw end ---");
    }

    @Override
    public String getMessage() {
        //System.out.println(" --- getMessage start ---");
        //return getCause().getMessage();
        return null;//"bob";//this.message;
    }

}
