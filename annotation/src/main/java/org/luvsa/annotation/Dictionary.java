package org.luvsa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 *
 * @author Aglet
 * @create 2022/7/18 21:59
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {

    String value() default "";

    String alias() default "";

    String scope() default "Normal";

    boolean capital() default false;
}
