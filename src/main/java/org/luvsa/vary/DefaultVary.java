package org.luvsa.vary;

import org.luvsa.vary.other.OFactory;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/29 16:50
 */
public class DefaultVary extends Manager<Factory<?>> implements Vary {

    private static final Factory<?> DEFAULT = new OFactory();

    static final Vary instance = new DefaultVary();

    @Override
    public <T> Object apply(T value, DataType type) {
        //如果值为空,直接返回默认值
        if (value == null) {
            return null;
        }

        //获取当前数据的Class对象
        var clz = value.getClass();
        if (type.isAssignableFrom(clz)) {
            return value;
        }

        // 对应的类型转换器
        var factory = offer(clz);
        if (factory == null) {
            // 如果没有转换器,这里可以抛一个异常,也可以返回默认值
            factory = DEFAULT;
        }

        // 创建转换函数
        @SuppressWarnings("unchecked")
        var fun = (Function<T, ?>) factory.create(type);

        //当转换的数据为空时,返回默认值,否则返回转换值
        return fun.apply(value);
    }

    @Override
    protected Factory<?> offer(Class<?> clazz) {
        if (cache.isEmpty()) {
            // 加载 转换器工厂
            synchronized (cache) {
                if (cache.isEmpty()) {
                    load(Factory.class, cache::put);
                }
            }
        }
        return cache.get(clazz);
    }
}
