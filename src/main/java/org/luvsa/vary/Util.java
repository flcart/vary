package org.luvsa.vary;

import java.util.Map;

/**
 * @author Aglet
 * @create 2022/7/15 11:27
 */
class Util {

    private final static Map<Class<?>, Class<?>> PRIMITIVES = Map.of(
            byte.class, Byte.class,
            short.class, Short.class,
            int.class, Integer.class,
            long.class, Long.class,
            float.class, Float.class,
            double.class, Double.class,
            char.class, Character.class,
            boolean.class, Boolean.class
    );

    public static Class<?> wrap(Class<?> clazz) {
        return PRIMITIVES.getOrDefault(clazz, clazz);
    }

}
