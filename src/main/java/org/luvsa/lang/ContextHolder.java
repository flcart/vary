package org.luvsa.lang;

import org.luvsa.reflect.Generics;
import org.luvsa.vary.Vary;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程缓存工具
 * @author Aglet
 * @create 2022/9/21 10:27
 */
public final class ContextHolder {

    private final static Map<Class<?>, Holder<?>> CACHE = new ConcurrentHashMap<>();

    static {
        var load = ServiceLoader.load(Holder.class);
        for (var item : load) {
            var aClass = item.getClass();
            Generics.accept(aClass, 0, clazz -> CACHE.put(clazz, item));
        }
    }

    private ContextHolder() {
    }

    public static <T> T get(Class<T> clazz) {
        var holder = CACHE.get(clazz);
        if (holder == null) {
            return null;
        }
        var o = holder.get();
        return Vary.change(o, clazz);
    }

    public static void set(Object o) {
        var aClass = o.getClass();
        var holder = CACHE.computeIfAbsent(aClass, DefaultHolder::new);
        holder.set(o);
    }

    private static class DefaultHolder implements Holder<Object> {

        private final static ThreadLocal<Object> LOCAL = new ThreadLocal<>();

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
            LOCAL.set(o);
        }

        @Override
        public String toString() {
            return type.getSimpleName();
        }
    }
}
