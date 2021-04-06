package io.videofirst.uitests.bddexp.gen2.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Test;

/**
 * @author Bob Marks
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Test
public @interface Scenario {

    int id() default -1;

    String name() default "";

    String description() default "";

}
