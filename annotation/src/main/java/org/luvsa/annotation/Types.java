package org.luvsa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Aglet
 * @create 2023/1/3 10:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Types {
    /**
     * 可供数据转换的数据类型
     *
     * @return 数据类型
     */
    Class<?>[] value();
}
