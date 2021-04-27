package io.videofirst.vfa.annotation.person;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Builder {

    /**
     * @return Name of generated builder class. Defaults to <code>[ClassName]Builder</code>.
     */
    String name() default "";

    /**
     * @return Name of method used for building the object. Defaults to <code>build</code>.
     */
    String methodName() default "build";
}
