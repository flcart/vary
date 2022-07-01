package org.luvsa.vary;

import java.lang.reflect.Type;

/**
 * @author Aglet
 * @create 2022/7/1 14:53
 */
public interface DataType {

    Class<?> getClazz();

    Type getGenericType();

    default boolean isAssignableFrom(Class<?> clz) {
        return getClazz().isAssignableFrom(clz);
    }
}
