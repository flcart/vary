package org.luvsa.vary.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Aglet
 * @create 2022/7/15 10:01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {

    String value() default "";

    String code() default "";

    Class<? extends Parser> parser() default ParserImpl.class;

}
