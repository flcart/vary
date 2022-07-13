package org.luvsa.vary.other;

import org.luvsa.vary.Conversion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/13 11:51
 */
public class SpecifyMethod implements OProvider {

    private final Map<String, Method> cache = new ConcurrentHashMap<>();

    @Override
    public boolean test(Class<?> clazz) {
        return clazz.isAnnotationPresent(Conversion.class);
    }

    @Override
    public Function<Object, ?> get(Class<?> clazz) {
        var anno = clazz.getAnnotation(Conversion.class);
        var guid = anno.value();
        return o -> {
            var method = fetch(o, guid);
            if (method == null) {
                throw new UnsupportedOperationException();
            }
            try {
                return method.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private Method fetch(Object o, String name) {
        var aClass = o.getClass();
        var className = aClass.getName();
        var key = className + "#" + name;
        var method = cache.get(key);
        if (method == null) {
            var methods = aClass.getDeclaredMethods();
            for (var item : methods) {
                var anno = item.getAnnotation(Conversion.class);
                if (anno == null) {
                    continue;
                }
                cache.put(className + "#" + item.getName(), item);
            }
            return cache.get(key);
        }
        return method;
    }
}
