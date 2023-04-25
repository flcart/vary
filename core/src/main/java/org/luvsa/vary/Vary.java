package org.luvsa.vary;

import org.luvsa.lang.ContextHolder;
import org.luvsa.reflect.Reflections;
import org.springframework.lang.NonNull;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;

/**
 * 数据转化接口
 *
 * @author Aglet
 * @create 2022/6/29 16:47
 */
public interface Vary {

    /**
     * 转换数据
     *
     * @param value 当前数据
     * @param cls   目标数据类型
     * @param <T>   当前数据类型
     * @param <R>   目标数据类型
     * @return 目标数据
     */
    @SuppressWarnings("unchecked")
    static <T, R> R change(T value, Class<R> cls) {
        if (value == null) {
            return null;
        }
        var target = Objects.requireNonNull(cls, "Target type can't be null!");
        var wrap = Reflections.wrap(target);
        if (wrap.isInstance(value)) {
            return (R) value;
        }
        return (R) convert(value, wrap);
    }

    static <K, V> Map<K, V> map(Collection<V> collection, Function<V, K> key) {
        return map(collection, key, item -> item);
    }

    static <T, K, V> Map<K, V> map(Collection<T> collection, Function<T, K> key, Function<T, V> value) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyMap();
        }
        var map = new HashMap<K, V>(collection.size());
        for (var item : collection) {
            var k = key.apply(item);
            var v = value.apply(item);
            map.put(k, v);
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * 转换数据
     *
     * @param value 当前数据
     * @param type  目标数据类型（包含目标数据类型信息）
     * @param <T>   当前数据类型
     * @return 目标数据
     */
    static <T> Object convert(@NonNull T value, Type type) {
        // 如果转换器不存在， 一定是报错， 不能返回一空值
        for (var item : Util.list) {
            if (!item.enabled()) {
                continue;
            }
            try {
                return item.infer(value, type);
            } catch (Exception e) {
                ContextHolder.set(e);
            }
        }
        var ex = ContextHolder.get(Exception.class);
        if (ex instanceof RuntimeException e) {
            throw e;
        }
        throw new RuntimeException(ex);
    }

    @SuppressWarnings("unchecked")
    static <T, R> R[] transform(List<T> list, Class<R> cls) {
        var size = list.size();
        var instance = Array.newInstance(cls, size);
        for (int i = 0; i < list.size(); i++) {
            var t = list.get(i);
            var v = change(t, cls);
            Array.set(instance, i, v);
        }
        return (R[]) instance;
    }

    static <T> List<T> asList(Object target, Class<T> clazz) {
        return asList(target, o -> Vary.change(o, clazz));
    }

    static <T> List<T> asList(Object target, Function<Object, T> function) {
        if (target == null) {
            return null;
        }
        List<T> rest = new ArrayList<>();
        var aClass = target.getClass();
        if (target instanceof List<?> list) {
            for (var o : list) {
                var change = function.apply(o);
                rest.add(change);
            }
        } else if (aClass.isArray()) {
            for (int i = 0, size = Array.getLength(target); i < size; i++) {
                var item = Array.get(target, i);
                var change = function.apply(item);
                rest.add(change);
            }
        }
        return rest;
    }

    default boolean enabled() {
        return true;
    }

    /**
     * 转换数据
     *
     * @param value 当前数据
     * @param dev   转换失败时默认返回数据
     * @param <T>   当前数据类型
     * @param <R>   目标数据类型
     * @return 目标数据
     */
    static <T, R> R apply(T value, R dev) {
        return Optional.ofNullable(value).map(t -> {
            if (dev == null) {
                return null;
            }
            @SuppressWarnings("unchecked")
            var clazz = (Class<R>) dev.getClass();
            return change(value, clazz);
        }).orElse(dev);
    }

    /**
     * 返回一个子数据，当转换失败时，返回当前数据，否则返回转换成功的数据
     *
     * @param value 当前数据
     * @param clazz 目标数据类型
     * @param <T>   当前数据类型
     * @param <R>   目标数据类型
     * @return 目标数据
     */
    static <T, R> Object realize(T value, Class<R> clazz) {
        try {
            var rtn = change(value, clazz);
            return rtn == null ? value : rtn;
        } catch (Exception e) {
            return value;
        }
    }

    default <T> Object infer(T value, Type type) throws Exception {
        //如果值为空,直接返回默认值
        if (value == null) {
            return null;
        }
        //获取当前数据的Class对象
        var clz = Reflections.wrap(value.getClass());
        if (checkAssignable(clz, type)) {
            // 当前数据与目标数据存在继承或者实现关系
            return value;
        }
        return apply(value, clz, type);
    }


    /**
     * 判断当前 {@link Class 数据类型} 与 目标数据类型是否存在 继承 或者实现关系
     *
     * @param clz  当前数据的 Class 对象
     * @param type 目标数据 类型
     * @return true：当前数据 为目标数据类型的一个实例
     */
    default boolean checkAssignable(Class<?> clz, Type type) {
        if (type instanceof Class<?> cls) {
            if (cls == clz) {
                return true;
            }
            var wrap = Reflections.wrap(cls);
            return wrap.isAssignableFrom(clz);
        }
        return false;
    }

    /**
     * 转换数据
     *
     * @param value 当前数据
     * @param clazz 当前数据的类型
     * @param type  目标数据类型(包含目标数据类型信息)
     * @param <T>   当前数据类型
     * @return 目标数据
     */
    <T> Object apply(T value, Class<?> clazz, Type type) throws Exception;

}
