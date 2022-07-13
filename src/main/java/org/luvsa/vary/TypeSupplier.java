package org.luvsa.vary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * 数据转换类型支持
 *
 * 自动转换
 * conversion
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

    /**
     * 默认下一步处理器
     * 创建
     * @param clazz 目标数据类型
     * @return 数据转换函数
     */
    default Function<Object, Object> found(Class<?> clazz) {
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
