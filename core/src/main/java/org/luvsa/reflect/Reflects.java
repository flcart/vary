package org.luvsa.reflect;

import org.luvsa.exception.ValueException;
import org.luvsa.lang.Strings;
import org.luvsa.vary.Vary;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Aglet
 * @create 2022/7/13 18:27
 */
public final class Reflects {
    private Reflects() {
        throw new AssertionError("No " + Reflects.class + " instances for you!");
    }

    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final String GET_METHOD_PREFIX = "get";
    private static final String GET_BOOL_METHOD_PREFIX = "is";

    private static final ParameterNameDiscoverer DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 查询 get 方法
     */
    final static BiPredicate<String, Method> FIND_GET_METHOD = (name, method) -> {
        if (method.getParameterCount() > 0) {
            return false;
        }
        var type = method.getReturnType();
        if (type == void.class || type == Void.class) {
            return false;
        }
        var methodName = method.getName();
        if (Objects.equals(methodName, name)) {
            return true;
        }
        var capitalize = Strings.capitalize(name);
        if (Objects.equals(GET_METHOD_PREFIX + capitalize, methodName)) {
            return true;
        }
        if (type == boolean.class || type == Boolean.class) {
            return Objects.equals(GET_BOOL_METHOD_PREFIX + capitalize, methodName);
        }
        return false;
    };

    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);

    /**
     * 在指定类中查找指定【名称】的方法
     *
     * @param clazz 类对象
     * @param name  方法名称
     * @return 方法对象， 如果没有找到返回 null
     */
    public static Method findMethod(Class<?> clazz, String name) {
        return ReflectionUtils.findMethod(clazz, name);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        return ReflectionUtils.findMethod(clazz, name, paramTypes);
    }

    public static Method findMethod(Class<?> clazz, Predicate<Method> predicate) {
        var search = clazz;
        while (search != null) {
            var methods = search.isInterface() ? search.getMethods() : getDeclaredMethods(search);
            for (var method : methods) {
                if (predicate.test(method)) {
                    return method;
                }
            }
            search = search.getSuperclass();
        }
        return null;
    }

    /**
     * 尝试在 {@link Class} 的 name 上查找。 field搜索所有Object超类，最多 。
     *
     * @param clazz 类对象
     * @param name  成员名称
     * @return Field 或 null
     */
    public static Field findField(Class<?> clazz, String name) {
        return ReflectionUtils.findField(clazz, name);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        return ReflectionUtils.findField(clazz, name, type);
    }

    public static Field findField(Class<?> clazz, Predicate<Field> predicate) {
        try {
            ReflectionUtils.doWithFields(clazz, field -> {
                if (predicate.test(field)) {
                    throw new ValueException(field);
                }
            });
            return null;
        } catch (ValueException e) {
            var value = e.getValue();
            return Vary.change(value, Field.class);
        }
    }

    public static Map<String, Object> extract(Object o) {
        return Collections.emptyMap();
    }

    public static boolean isPublicStaticFinal(Field field) {
        return ReflectionUtils.isPublicStaticFinal(field);
    }

    public static Object invokeMethod(Method method, Object target) {
        return ReflectionUtils.invokeMethod(method, target);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        return ReflectionUtils.invokeMethod(method, target, args);
    }

    public static void doWithFields(Class<?> clazz, Consumer<Field> consumer) {
        ReflectionUtils.doWithFields(clazz, consumer::accept);
    }

    public static void doWithFields(Class<?> clazz, Consumer<Field> consumer, Predicate<Field> predicate) {
        ReflectionUtils.doWithFields(clazz, consumer::accept, predicate::test);
    }

    public static void doWithMethods(Class<?> clazz, Consumer<Method> consumer) {
        ReflectionUtils.doWithMethods(clazz, consumer::accept);
    }

    public static void doWithMethods(Class<?> clazz, Consumer<Method> consumer, Predicate<Method> predicate) {
        ReflectionUtils.doWithMethods(clazz, consumer::accept, predicate::test);
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return ReflectionUtils.getDeclaredMethods(clazz);
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> clazz, Predicate<Method> predicate) {
        return ReflectionUtils.getUniqueDeclaredMethods(clazz, predicate::test);
    }

    public static boolean isCglibRenamedMethod(Method renamedMethod) {
        return ReflectionUtils.isCglibRenamedMethod(renamedMethod);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> type, int size) {
        return (T[]) Array.newInstance(type, size);
    }

    /**
     * 从目标对象中获取指定 成员名称 的值
     *
     * @param name   成员名称，属性
     * @param target 目标对象
     * @return 值
     */
    public static Object getField(String name, Object target) {
        var aClass = target.getClass();
        try {
            doWithMethods(aClass, method -> {
                if (FIND_GET_METHOD.test(name, method)) {
                    var o = invokeMethod(method, target);
                    throw new ValueException(o);
                }
            });
            var field = findField(aClass, name);
            if (field == null) {
                return null;
            }
            return getField(field, target);
        } catch (ValueException e) {
            return e.getValue();
        }
    }

    public static void setValue(Field field, Object source, Object value) {
        if (value == null) {
            setValue0(field, source, null);
        } else {
            var name = field.getName();
            var clazz = field.getDeclaringClass();
            var capitalize = Strings.capitalize(name);
            var method = ReflectionUtils.findMethod(clazz, "set" + capitalize, field.getType());
            if (method == null) {
                setValue0(field, source, value);
            } else {
                ReflectionUtils.invokeMethod(method, source, value);
            }
        }
    }

    private static void setValue0(Field field, Object source, Object o) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, source, null);
    }


    /**
     * 获取指定类的成员属性
     *
     * @param field  类成员
     * @param target 实列
     * @return 属性值
     */
    public static Object getValue(Field field, Object target) {
        if (field == null) {
            return null;
        }
        try {
            var clazz = field.getDeclaringClass();
            var name = field.getName();
            Reflects.doWithMethods(clazz, method -> {
                if (Reflects.FIND_GET_METHOD.test(name, method)) {
                    var o = Reflects.invokeMethod(method, target);
                    throw new ValueException(o);
                }
            });
            return Reflects.getField(field, target);
        } catch (ValueException e) {
            return e.getValue();
        }
    }

    public static Object getField(Field field, Object target) {
        return ReflectionUtils.getField(field, target);
    }

    public static String[] discover(Method method) {
        return DISCOVERER.getParameterNames(method);
    }
}
