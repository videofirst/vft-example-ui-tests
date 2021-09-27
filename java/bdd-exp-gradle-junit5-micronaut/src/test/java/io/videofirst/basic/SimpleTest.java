package io.videofirst.basic;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Bob Marks
 */
public class SimpleTest {

    {
        PrintStream dummyStream = new PrintStream(new OutputStream() {
            public void write(int b) {
                // NO-OP
            }
        });

        //System.setOut(dummyStream);
        //System.setErr(dummyStream);
    }

    @Test
    public void test1() throws Exception {
        //System.out.println("1: Standard Out Message");
        //System.out.println("1: Standard Error Message");
        Assertions.assertTrue(true);
    }

    // https://github.com/google/truth/issues/680
    // https://youtrack.jetbrains.com/issue/IDEA-238472 (IntelliJ duplicates certain assertion failures)

    @Test
    public void test2() throws Exception {
        //System.out.println("2: Standard Out Message");
        //System.out.println("2: Standard Error Message");
        try {
            Assertions.assertTrue(false);

        } catch (AssertionError t) {
            throw new CustomAssertionFailedError(t);
        }
    }

    @Test
    public void test3() throws Exception {
        ////System.out.println("2: Standard Out Message");
        ////System.out.println("2: Standard Error Message");
        throw new CustomException("bob test");
        //Assertions.assertTrue(false);
    }

}

class CustomException extends RuntimeException {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        //System.out.println(" --- printStackTrace start ---");
        super.printStackTrace();
        //System.out.println(" --- printStackTrace end ---");
    }

    @Override
    public void printStackTrace(PrintStream ps) {
        //System.out.println(" --- printStackTrace ps start ---");
        super.printStackTrace(ps);
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
        return this.message;
    }

}

class CustomAssertionFailedError extends AssertionError {

    private String message;

    public CustomAssertionFailedError(AssertionError t) {
        super(t);
        this.message = t.getMessage();
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

