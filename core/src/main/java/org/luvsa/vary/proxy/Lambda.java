package org.luvsa.vary.proxy;

import org.luvsa.reflect.Reflects;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/15 14:18
 */
public class Lambda implements Parser {

    public final static String SIGN_INVOKE = "::";

    private final Map<String, Class<?>> classes = new HashMap<>(8);

    @Override
    public Function<Object, ?> create(String code) {
        if (code.contains(SIGN_INVOKE)) {
            var index = code.indexOf(SIGN_INVOKE);
            var key = code.substring(0, index);
            var val = code.substring(index + SIGN_INVOKE.length());
            if (Character.isUpperCase(key.charAt(0))) {
                // 静态方法调用
                var clazz = classes.get(key);
                var method = Reflects.findMethod(clazz, method1 -> Objects.equals(method1.getName(), val));
                var modifiers = method.getModifiers();
                return o -> {
                    if (Modifier.isStatic(modifiers)) {
                        return Reflects.invokeMethod(method, null, o);
                    }
                    return Reflects.invokeMethod(method, o);
                };
            }
        }
        throw new UnsupportedOperationException("暂时不支持 lambda[" + code + "] 表达式的解析");
    }

    @Override
    public void register(Object o) {
        if (o instanceof Class<?> clazz) {
            register0(clazz);
        } else if (o instanceof Method method) {
            register0(method.getDeclaringClass());
            register0(method.getReturnType());
        } else {
            register0(o.getClass());
        }
    }

    private void register0(Class<?> clazz) {
        var current = clazz;
        while (current != null) {
            classes.put(current.getSimpleName(), current);
            classes.put(current.getName(), current);
            if (clazz.isInterface()) {
                var interfaces = clazz.getInterfaces();
                for (var anInterface : interfaces) {
                    register(anInterface);
                }
            }
            current = current.getSuperclass();
        }
    }
}
