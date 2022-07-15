package org.luvsa.vary;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 目标数据信息接口
 *
 * @author Aglet
 * @create 2022/7/1 14:53
 */
public interface DataType {
    static DataType of(Type type) {
        if (type instanceof Class<?> clazz) {
            return new Clazz(clazz);
        }

        if (type instanceof ParameterizedType param) {
            var rawType = param.getRawType();
            var ownerType = param.getOwnerType();
            var arguments = param.getActualTypeArguments();
            return new GenericType(null, type);
        }

        throw new IllegalStateException(type.toString());
    }

    /**
     * 获取目标数据类型
     *
     * @return 数据类型
     */
    Class<?> getClazz();

    /**
     * 获取目标泛型参数类型
     *
     * @return 型参数类型(如果不是泛型 ， 则返回 null)
     */
    Type getGenericType();

    /**
     * 检测 {@link #getClazz() 目标数据类型}  与 {@link Class clz} 是否存在 继承或实现关系(如果{@link #getClazz() 目标数据类型}
     * 和 {@link Class clz} 相同，返回 true)。基于 {@link Class#isAssignableFrom(Class)} 实现
     *
     * @param clz 目标数据类型
     * @return {@link Class#isAssignableFrom(Class)}
     */
    default boolean isAssignableFrom(Class<?> clz) {
        var clazz = getClazz();
        if (clazz == byte.class) {
            return Byte.class.isAssignableFrom(clz);
        }
        if (clazz == short.class) {
            return Short.class.isAssignableFrom(clz);
        }
        if (clazz == int.class) {
            return Integer.class.isAssignableFrom(clz);
        }
        if (clazz == long.class) {
            return Long.class.isAssignableFrom(clz);
        }
        if (clazz == float.class) {
            return Float.class.isAssignableFrom(clz);
        }
        if (clazz == double.class) {
            return Double.class.isAssignableFrom(clz);
        }
        if (clazz == char.class) {
            return Character.class.isAssignableFrom(clz);
        }
        if (clazz == boolean.class) {
            return Boolean.class.isAssignableFrom(clz);
        }
        return clazz.isAssignableFrom(clz);
    }

}
