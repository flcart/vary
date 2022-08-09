package org.luvsa.vary;

import org.luvsa.reflect.Generics;

import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 18:59
 */
public abstract class AbstractFactory<T, R extends Provider<T>> extends Manager<R> implements Factory<T> {

    /**
     * 默认错误数据转换函数
     */
    private final static BiFunction<Object, Type, ?> error = (o, clz) -> {
        throw new UnsupportedOperationException("不支持 [" + o.getClass() + " : " + o + "] -> [" + clz + "]");
    };

    @Override
    public Function<T, ?> create(Type type) {
        // 获取 转换函数
        try {
            var provider = offer(type);

            if (provider == null) {
                // 缓存中没有指定数据类型的转换函数
                return next(type);
            }

            var function = provider.get(type);
            if (function == null){
                return next(type);
            }

            return function;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void handle(R item) {
    }

    @Override
    protected R offer(Type type) throws Exception {
        if (this.isEmpty()) {
            // 获取 Provider
            Generics.accept(getClass(), 1, item -> {
                @SuppressWarnings("unchecked")
                var clazz = (Class<R>) item;
                loader.load(clazz, this::put, this::handle);
            });
        }
        return this.get(type);
    }

    /**
     * 在缓存中没有找到转换函数时调用， 属于一个替代方案
     *
     * @param type 目标数据类型
     * @return 转换函数
     */
    protected Function<T, ?> next(Type type) {
        return item -> error.apply(item, type);
    }
}
