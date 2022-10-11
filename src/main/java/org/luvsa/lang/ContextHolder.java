package org.luvsa.lang;

import org.luvsa.reflect.Generics;
import org.luvsa.reflect.Reflections;
import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.Vary;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程缓存工具
 *
 * @author Aglet
 * @create 2022/9/21 10:27
 */
public final class ContextHolder {

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

    public static <T> T get(Class<T> clazz) {
        var wrap = Reflections.wrap(clazz);
        var holder = CACHE.get(wrap);
        if (holder == null) {
            return null;
        }
        var o = holder.get();
        return Vary.change(o, clazz);
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
