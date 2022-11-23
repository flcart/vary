package org.luvsa.vary;

import org.luvsa.exception.FactoryNotFoundException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/11/21 14:48
 */
public class StructVary implements Vary {

    private static final Map<Class<?>, Factory> cache = new ConcurrentHashMap<>();

    /**
     * 添加 数据转换工厂
     * @param clazz 当前数据类型，（需要进行数据转换的数据类型）
     * @param type
     * @param method
     * @param mapper
     */
    public static void accept(Class<?> clazz, Type type, Method method, Object mapper) {
        var factory = cache.computeIfAbsent(clazz, Factory::new);
        factory.put(type, new Provider(method, mapper));
    }

    @Override
    public <T> Object apply(T value, Class<?> clazz, Type type) throws Exception {
        var factory = cache.get(clazz);
        if (factory == null) {
            throw new FactoryNotFoundException(clazz);
        }
        var function = factory.create(type);
        return function.apply(value);
    }

    private static class Factory extends AbstractFactory<Object, Provider> {

        private final Class<?> type;

        public Factory(Class<?> source) {
            this.type = source;
        }

        public Class<?> source() {
            return type;
        }

        @Override
        protected void put(Type key, Provider value) {
            super.put(key, value);
        }

        @Override
        public String toString() {
            return type + " function factory";
        }
    }

    private record Provider(Method method, Object instance) implements org.luvsa.vary.Provider<Object> {

        @Override
        public Function<Object, ?> get(Type type) {
            return o -> ReflectionUtils.invokeMethod(method, instance, o);
        }
    }
}
