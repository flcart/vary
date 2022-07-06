package org.luvsa.vary.other;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * key
 *
 * @author Aglet
 * @create 2022/7/6 17:05
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {

    /**
     * 对应的 key 值
     *
     * @return key
     */
    String value();

    /**
     * 默认别名，
     *
     * @return 别名
     */
    String alias() default "";
}
