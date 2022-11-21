package org.luvsa.lang;

import org.luvsa.reflect.Generics;
import org.luvsa.reflect.Reflections;
import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.Vary;

import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程缓存工具
 *
 * @author Aglet
 * @create 2022/9/21 10:27
 */
public final class ContextHolder {
    /**
     * 线程缓存器的缓存容器
     */
    private final static Map<Class<?>, Holder<?>> CACHE = new ConcurrentHashMap<>();

    static {
        var load = ServiceLoader.load(Holder.class);
        for (var item : load) {
            var aClass = item.getClass();
            var types = aClass.getAnnotation(Types.class);
            if (types == null) {
                Generics.accept(aClass, 0, type -> CACHE.put(type, item));
            } else {
                for (var clas : types.value()) {
                    CACHE.put(clas, item);
                }
            }
        }
    }

    private ContextHolder() {
    }

    /**
     * 获取当前线程的缓存
     *
     * @param def 如果当前线程缓存为空的默认返回值
     * @param <T> 数据类型
     * @return 获取当前线程的缓存
     */
    public static <T> T get(T def) {
        if (def == null) {
            throw new IllegalArgumentException();
        }
        var aClass = def.getClass();
        var o = get0(aClass);
        if (o == null) {
            return def;
        }
        @SuppressWarnings("unchecked")
        var clz = (Class<? extends T>) aClass;
        return Vary.change(o, clz);
    }

    private static Object get0(Class<?> clazz) {
        var wrap = Reflections.wrap(clazz);
        var holder = fetch(wrap);
        if (holder == null) {
            return null;
        }
        return holder.get();
    }


    public static <T> T get(Class<T> clazz) {
        var t = get0(clazz);
        return Vary.change(t, clazz);
    }

    public static <T> T getOrThrow(Class<T> clazz, String message) {
        var t = get(clazz);
        return Objects.requireNonNull(t, message);
    }

    private static Holder<?> fetch(Class<?> clazz) {
        var holder = CACHE.get(clazz);
        if (holder != null) {
            return holder;
        }
        for (var item : CACHE.keySet()) {
            if (clazz.isAssignableFrom(item)) {
                holder = CACHE.get(item);
                CACHE.put(clazz, holder);
                return holder;
            }
        }
        return null;
    }

    public static void set(Object o) {
        var aClass = o.getClass();
        var holder = CACHE.computeIfAbsent(aClass, DefaultHolder::new);
        holder.put(o);
    }

    /**
     * Holder 的默认实现方式
     */
    private final static class DefaultHolder implements Holder<Object> {
        /**
         * 基于线程的缓存控制
         */
        private final ThreadLocal<Object> LOCAL = new ThreadLocal<>();

        private final Class<?> type;

        public DefaultHolder(Class<?> key) {
            this.type = key;
        }

        @Override
        public Object get() {
            return LOCAL.get();
        }

        @Override
        public void set(Object o) {
            next(o);
        }

        private void next(Object o) {
            var aClass = o.getClass();
            if (type.isAssignableFrom(aClass)) {
                LOCAL.set(o);
            } else {
                throw new IllegalArgumentException("无法将 [" + aClass.getSimpleName() + " : " + o + "] 数据设置到 " + this + " 容器中");
            }
        }

        @Override
        public void put(Object o) {
            next(o);
        }

        @Override
        public String toString() {
            return type.getSimpleName();
        }
    }
}
