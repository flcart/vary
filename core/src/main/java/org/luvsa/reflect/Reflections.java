package org.luvsa.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/14 18:34
 */
public final class Reflections {
    /**
     * 原始数据类型
     */
    final static Map<Class<?>, Class<?>> PRIMITIVES = Map.of(
            byte.class, Byte.class,
            short.class, Short.class,
            int.class, Integer.class,
            long.class, Long.class,
            float.class, Float.class,
            double.class, Double.class,
            char.class, Character.class,
            boolean.class, Boolean.class
    );

    final static Map<String, Class<?>> cache = Map.of(
            "byte", Byte.class,
            "short", Short.class,
            "int", Integer.class,
            "integer", Integer.class,
            "long", Long.class,
            "float", Float.class,
            "double", Double.class,
            "char", Character.class,
            "boolean", Boolean.class,
            "bool", Boolean.class
    );

    private Reflections() {
        throw new AssertionError("No " + Reflections.class + " instances for you!");
    }

    /**
     * 装箱，对于基本数据类型需要进行装箱操作， 如果不是基本数据类型， 直接返回
     *
     * @param clazz Class 对象
     * @return 装箱或者原来的数据类型
     */
    public static Class<?> wrap(Class<?> clazz) {
        return PRIMITIVES.getOrDefault(clazz, clazz);
    }

    public static Class<?> type(String type) {
        var aClass = cache.get(type.toLowerCase());
        if (aClass != null) {
            return aClass;
        }
        try {
            return Class.forName(type);
        } catch (Exception e) {
            throw new IllegalArgumentException("无法识别类型 【" + type + "】", e);
        }
    }

    public static boolean isPrimitive(Class<?> clazz) {
        return PRIMITIVES.containsKey(clazz) || PRIMITIVES.containsValue(clazz);
    }

    /**
     * 获取 调用目标 类方法的上层 类的 class 对象，
     * 实现 {@link jdk.internal.reflect.Reflection#getCallerClass() 获取调用者的Class} 功能
     *
     * @param clazz 被调用的 class 对象
     * @return 调用者的class对象
     */
    public static Class<?> getCallerClass(Class<?> clazz) {
        var found = false;
        var name = clazz.getName();
        var trace = Thread.currentThread().getStackTrace();
        for (var item : trace) {
            var className = item.getClassName();
            if (Objects.equals(className, name)) {
                found = true;
            } else if (found) {
                try {
                    return Class.forName(className);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new IllegalArgumentException("无法找到调用 " + clazz + " 的类对象！");
    }

    /**
     * 创建指定类名的实列对象
     *
     * @param name 类名
     * @return 实例对象
     */
    public static Object newInstance(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        try {
            var clazz = Class.forName(name);
            return newInstance(clazz);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            var constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            return null;
        }
    }


}
