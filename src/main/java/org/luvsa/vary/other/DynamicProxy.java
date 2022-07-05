package org.luvsa.vary.other;

import org.luvsa.vary.GenericType;
import org.luvsa.vary.Vary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Function;

/**
 * 动态代理实现数据与接口类型
 *
 * @author Aglet
 * @create 2022/6/30 16:30
 */
public class DynamicProxy implements OProvider {

    @Override
    public Function<Object, ?> get(Class<?> clazz) {
        return o -> Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyInvocationHandler(o));
    }

    private record ProxyInvocationHandler(Object source) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var anno = method.getAnnotation(MethodName.class);
            var name = anno == null ? method.getName() : anno.value();
            var size = args == null ? 0 : args.length;
            var types = new Class<?>[size];
            for (int i = 0; i < size; i++) {
                types[i] = args[i].getClass();
            }
            var aClass = source.getClass();

            var target = aClass.getMethod(name, types);

            var invoke = target.invoke(source, args);
            var cur = target.getReturnType();
            var tar = method.getReturnType();
            if (tar.isAssignableFrom(cur)) {
                return invoke;
            }
            if (anno == null) {
                return null;
            }
            var code = anno.code();
            if (code.isBlank()) {
                return null;
            }
            var parser = new Parser(code, Map.of("o", invoke));
            var o = parser.get();
            if (anno.generics()) {
                return Vary.change(o, new GenericType(tar, method.getGenericReturnType()));
            }
            return Vary.change(o, tar);
        }
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MethodName {

        String value();

        String code() default "";

        boolean generics() default false;
    }
}
