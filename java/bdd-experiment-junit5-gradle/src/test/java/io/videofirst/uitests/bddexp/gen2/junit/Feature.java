package io.videofirst.uitests.bddexp.gen2.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;

/**
 * @author Bob Marks
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Testable
@ExtendWith(FeatureExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // FIXME - Custom version of this
public @interface Feature {

    long id() default -1;

    String name() default "";

    String description() default "";

}
