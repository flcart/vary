package org.luvsa.lang;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * @author Aglet
 * @create 2022/12/19 17:17
 */
@Inherited
@Documented
@JacksonAnnotationsInside
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = MateJsonSerializer.class)
public @interface Sensitive {

    /**
     * 脱敏策略名称
     *
     * @return 策略名称
     */
    String value() default "";

    String pattern() default "";
    String format() default "";
}
