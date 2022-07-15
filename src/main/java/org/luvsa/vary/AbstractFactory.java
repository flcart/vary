package org.luvsa.vary;

import org.luvsa.reflect.Generics;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 18:59
 */
public abstract class AbstractFactory<T, R extends Provider<T>> implements Factory<T> {

    /**
     * Provider 缓存
     */
    protected final Cache<T, R> map = new Cache<>(this::init);

    /**
     * 默认错误数据转换函数
     */
    private final static BiFunction<Object, Class<?>, ?> error = (o, clz) -> {
        throw new UnsupportedOperationException("不支持 [" + o.getClass() + " : " + o + "] -> [" + clz + "]");
    };

    @Override
    public Function<T, ?> create(DataType type) {
        var clazz = type.getClazz();
        // 获取 转换函数
        var function = map.getFunction(clazz);
        if (function == null) {
            // 缓存中没有指定数据类型的转换函数
            return next(clazz);
        }
        return function;
    }

    protected void handle(R item) {
    }

    /**
     * 在缓存中没有找到转换函数时调用， 属于一个替代方案
     *
     * @param clazz 目标数据类型
     * @return 转换函数
     */
    protected Function<T, ?> next(Class<?> clazz) {
        return item -> error.apply(item, clazz);
    }

    /**
     * 缓存初始化调用
     *
     * @param initiator 初始化
     */
    private void init(BiConsumer<Class<?>, R> initiator) {
        // 获取 Provider
        Generics.accept(getClass(), 1, item -> {
            @SuppressWarnings("unchecked")
            var clazz = (Class<R>) item;
            // 加载 Provider
            map.next(clazz, this::handle);
        });

    }
}
