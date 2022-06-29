package org.luvsa.vary;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 容器
 *
 * @author Aglet
 * @create 2022/6/29 9:56
 */
public abstract class Cache<T, R extends Provider<T>> {

    private final Map<Class<?>, R> map = new HashMap<>();

    private BiConsumer<Class<R>, BiConsumer<Class<?>, R>> initiator;

    public Function<T, ?> get(Class<?> clazz) {
        if (map.isEmpty()) {
            @SuppressWarnings("unchecked")
            var cls = (Class<R>) getParameterizedType(1);
            initiator.accept(cls, map::put);
        }
        var provider = map.get(clazz);
        if (provider == null) {
            throw new UnsupportedOperationException("不支持由 [" + getParameterizedType(0) + "] -> [" + clazz + "] 的转换！");
        }
        return provider.get(clazz);
    }

    private Class<?> getParameterizedType(int index) {
        var aClass = getClass();
        var generic = aClass.getGenericSuperclass();
        //Parameterized Type
        if (generic instanceof ParameterizedType type) {
            var arguments = type.getActualTypeArguments();
            var argument = arguments[index];
            if (argument instanceof Class<?> clazz) {
                return clazz;
            }
        }
        throw new RuntimeException(aClass + " 没有泛型参数！");
    }

    public void register(BiConsumer<Class<R>, BiConsumer<Class<?>, R>> initiator){
        this.initiator = initiator;
    }
}
