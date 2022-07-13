package org.luvsa.vary.other;

import org.luvsa.vary.Conversion;
import org.luvsa.vary.Reflects;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/13 11:51
 */
public class SpecifyMethod implements OProvider {

    private final Map<Class<?>, Map<String, Method>> cache = new ConcurrentHashMap<>();

    @Override
    public boolean test(Class<?> clazz) {
        return clazz.isAnnotationPresent(Conversion.class);
    }

    @Override
    public Function<Object, ?> get(Class<?> clazz) {
        var anno = clazz.getAnnotation(Conversion.class);
        var guid = anno.value();
        return o -> invoke(o, guid);
    }

    private Object invoke(Object o, String name) {
        var index = name.indexOf("#");
        if (index < 0) {
            var method = find(o, name);
            return Reflects.invokeMethod(method, o);
        }
        var sub = name.substring(0, index);
        try {
            var aClass = Class.forName(sub);
            var methodName = name.substring(index + 1);
            var methods = Reflects.getUniqueDeclaredMethods(aClass, method -> Objects.equals(methodName, method.getName()));
            for (var method : methods) {
                try {
                    var types = method.getParameterTypes();
                    if (types.length == 1 && types[0].isAssignableFrom(o.getClass())) {
                        return Reflects.invokeMethod(method, null, o);
                    }
                    return Reflects.invokeMethod(method, null);
                } catch (Throwable ex) {
                    throw new IllegalStateException(name, ex);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException(name);
    }

    private Method find(Object o, String name) {
        var aClass = o.getClass();
        var map = cache.get(aClass);
        if (map == null || map.isEmpty()) {
            var temp = new HashMap<String, Method>();
            Reflects.doWithMethods(aClass, item -> {
                temp.put(item.getName(), item);
            }, item -> item.isAnnotationPresent(Conversion.class));
            if (temp.isEmpty()) {
                throw new IllegalStateException("类" + aClass.getName() + "中不存在带有[org.luvsa.vary.Conversion]注解的方法!");
            }
            cache.put(aClass, (map = temp));
        }
        var method = map.get(name);
        if (method != null) {
            return method;
        }
        if (!name.isBlank()) {
            throw new IllegalStateException("在" + aClass + "类中没有找到" + name + "方法");
        }
        if (map.size() != 1) {
            throw new IllegalStateException("无法确认您具体需要方法！");
        }
        for (var item : map.values()) {
            return item;
        }
        throw new IllegalStateException("无法运行到这里");
    }
}
