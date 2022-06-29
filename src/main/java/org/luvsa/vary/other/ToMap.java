package org.luvsa.vary.other;

import org.luvsa.vary.Strings;
import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.Vary;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 11:16
 */
@Types(Map.class)
public class ToMap implements OProvider, Function<Object, Map<String, Object>> {

    private final Map<Class<? extends Annotation>, Function<Object, Map<String, Object>>> map = Map.of(SupportIob.class, o -> {
        var clazz = o.getClass();
        var map = new HashMap<String, Object>();
        var methods = clazz.getDeclaredMethods();
        for (var method : methods) {
            var iob = method.getAnnotation(Iob.class);
            if (iob == null || !iob.show()) {
                continue;
            }
            var alias = iob.alias();
            var value = iob.value();
            var name = alias.isBlank() ? refer(method.getName()) : alias;
            try {
                var invoke = method.invoke(o);
                map.put(name, Map.of("name", value, "value", Vary.realize(invoke, Map.class)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                // zhi
            }
        }
        return map;
    }, SupportField.class, o -> {
        var clazz = o.getClass();
        var map = new HashMap<String, Object>();
        var fields = clazz.getDeclaredFields();
        for (var field : fields) {
            var name = field.getName();
            var methodName = "get" + Strings.capitalize(name);
            try {
                var method = clazz.getDeclaredMethod(methodName);
                var invoke = method.invoke(o);
                map.put(name, Vary.realize(invoke, Map.class));
            } catch (Exception e) {
                //
            }
        }
        return map;
    });

    @Override
    public Function<Object, ?> get(Class<?> clazz) {
        return this;
    }

    @Override
    public Map<String, Object> apply(Object o) {
        var aClass = o.getClass();
        var entries = map.entrySet();
        for (var entry : entries) {
            var key = entry.getKey();
            if (aClass.isAnnotationPresent(key)) {
                return entry.getValue().apply(o);
            }
        }
        throw new UnsupportedOperationException("不支持将数据【" + aClass + " ： " + o + "】 转换成 Map！提示： 您似乎漏了注解");
    }

    private String refer(String name) {
        if (name.startsWith("get")) {
            name = name.substring(3);
        }
        return Strings.uncapitalize(name);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Iob {

        String value() default "";

        String alias() default "";

        boolean show() default true;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SupportIob {
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SupportField {
    }
}
