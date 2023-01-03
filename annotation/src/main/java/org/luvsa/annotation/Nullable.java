package org.luvsa.annotation;

import java.lang.annotation.*;

/**
 * @author Aglet
 * @create 2023/1/3 10:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Nullable {

}
