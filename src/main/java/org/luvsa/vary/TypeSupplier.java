package org.luvsa.vary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/29 16:44
 */
public interface TypeSupplier {

    /**
     * 可供数据转换的数据类型
     *
     * @return 数据类型
     */
    default Class<?>[] getTypes() {
        return new Class[0];
    }

    default Function<Object, Object> next(Class<?> clazz) {
        return o -> Vary.change(o, clazz);
    }

    /**
     * 供转换的数据类型
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Types {
        /**
         * 可供数据转换的数据类型
         *
         * @return 数据类型
         */
        Class<?>[] value();
    }
}
