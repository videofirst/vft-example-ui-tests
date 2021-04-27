package io.videofirst.vfa.annotation.vfa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Vfa {

//    /**
//     * @return Name of generated builder class. Defaults to <code>[ClassName]Builder</code>.
//     */
//    String name() default "";

}
