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
        if (this.isEmpty()) {
            // 获取 Provider
            var aClass = this.getClass();
            Generics.submit(aClass, 1, item -> {
                if (item instanceof Class<?> c) {
                    @SuppressWarnings("unchecked")
                    Class<R> t = (Class<R>) c;
                    loader.load(t, this::put, this::handle);
                } else {
                    throw new IllegalArgumentException(item.getTypeName());
                }
            });
        }

        try {
            return this.get(type, r -> next(type, r));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void handle(R item) {
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

    protected Function<T, ?> next(Type type, R provider) {
        if (provider == null) {
            // 缓存中没有指定数据类型的转换函数
            return next(type);
        }
        var function = provider.get(type);
        if (function == null) {
            return next(type);
        }
        return function;
    }
}
