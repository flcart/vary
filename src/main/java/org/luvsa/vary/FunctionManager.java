package org.luvsa.vary;

import java.lang.reflect.ParameterizedType;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/30 15:30
 */
public abstract class FunctionManager<T, R extends Provider<T>> extends Manager<Function<T, ?>> {

    protected final Cache<T, R> map = new Cache<>(this::init);

    private final BiFunction<T, Class<?>, ?> error = (o, clz) -> {
        throw new UnsupportedOperationException("不支持 [" + o.getClass() + " : " + o + "] -> [" + clz + "]");
    };

    @Override
    protected Function<T, ?> offer(Class<?> clazz) {
        var function = map.getFunction(clazz);
        if (function == null) {
            return next(clazz);
        }
        return function;
    }

    protected Function<T, ?> next(Class<?> clazz) {
        return item -> error.apply(item, clazz);
    }

    private void init(BiConsumer<Class<?>, R> initiator) {
        var aClass = getClass();
        var generic = aClass.getGenericSuperclass();
        //Parameterized Type
        if (generic instanceof ParameterizedType type) {
            var arguments = type.getActualTypeArguments();
            var argument = arguments[1];
            if (argument instanceof Class) {
                @SuppressWarnings("unchecked")
                var clazz = (Class<R>) argument;
                load(clazz, initiator);
                return;
            }
        }
        throw new RuntimeException(aClass + " 没有泛型参数！");
    }
}
