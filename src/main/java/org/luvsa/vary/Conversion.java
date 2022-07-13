package org.luvsa.vary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定转换方法 Specify
 *
 * @author Aglet
 * @create 2022/7/13 10:33
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Conversion {

    String value() default "";

}
