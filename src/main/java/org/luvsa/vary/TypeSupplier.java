package org.luvsa.vary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
