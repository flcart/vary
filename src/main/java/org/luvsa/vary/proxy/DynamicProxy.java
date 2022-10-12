package org.luvsa.vary.proxy;

import org.luvsa.reflect.Reflections;
import org.luvsa.reflect.Reflects;
import org.luvsa.vary.Vary;
import org.luvsa.vary.other.Provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * 动态代理实现数据与接口类型
 *
 * @author Aglet
 * @create 2022/6/30 16:30
 */
public class DynamicProxy implements Provider {

    @Override
    public Function<Object, ?> get(Type type) {
        var clazz = (Class<?>) type;
        return o -> Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyInvocationHandler(o));
    }

    @Override
    public boolean test(Type type) {
        if (type instanceof Class<?> clazz) {
            return clazz.isInterface();
        }
        return false;
    }

    private record ProxyInvocationHandler(Object source) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 接口方法
            var anno = method.getAnnotation(Mapper.class);

            // 方法名称
            var name = getMethodName(method, anno );

//            anno == null ? method.getName() : anno.value();

            // 方法参数
            var size = args == null ? 0 : args.length;
            var types = new Class<?>[size];
            for (int i = 0; i < size; i++) {
                types[i] = args[i].getClass();
            }
            var clazz = source.getClass();
            var target = Reflects.findMethod(clazz, item -> {
                if (item.getParameterCount() != size || !Arrays.equals(types, item.getParameterTypes())) {
                    return false;
                }
                var tar = removePrefix(item.getName());
                var cur = removePrefix(name);
                return Objects.equals(tar, cur);
            });

            if (target == null) {
                var joiner = new StringJoiner(", ");
                var parameters = method.getParameters();
                for (int i = 0; i < size; i++) {
                    var item = parameters[i];
                    joiner.add(item.getType().getName() + ' ' + item.getName() + "/* " + args[i] + " */");
                }
                throw new NoSuchMethodException(clazz.getName() + '.' + name + "(" + joiner + ")");
            }
            var value = Reflects.invokeMethod(target, source, args);
            // 检查返回类型是否一样
            var change = beforeChange(method, value, anno);
            return Vary.convert(change, method.getGenericReturnType());
        }

        private String getMethodName(Method method, Mapper mapper) {
            if (mapper == null){
                return method.getName();
            }
            var value = mapper.value();
            if (value.isBlank()){
                return method.getName();
            }
            return value;
        }

        private Object beforeChange(Method method,  Object value, Mapper mapper) throws Exception {
            if (mapper == null) {
                return value;
            }
            // 执行字符串代码方案
            var code = mapper.code();
            if (code.isBlank()) {
                return value;
            }

            var parser = Reflections.newInstance(mapper.parser());
            if (parser == null){
                return value;
            }

            parser.register(method);
            parser.register(value);

            var function = parser.create(code);

//            // 1. 初始化本地变量表
//            // 2. 执行相应的方法
//            // 3. 返回执行结果
//            // 执行临时代码
//            var parser = new ParserImpl(code, Map.of("o", value));
//            return parser.get();

            return function.apply(value);
        }

        private String removePrefix(String target) {
            if (target.startsWith("get") || target.startsWith("set")) {
                return target.substring(3).toLowerCase();
            }
            if (target.startsWith("is")) {
                return target.substring(2).toLowerCase();
            }
            return target.toLowerCase();
        }
    }
}
