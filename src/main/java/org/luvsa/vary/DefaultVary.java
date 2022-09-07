package org.luvsa.vary;

import org.luvsa.exception.FactoryNotFoundException;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * 默认转换器实现入口
 *
 * @author Aglet
 * @create 2022/6/29 16:50
 */
public class DefaultVary extends Manager<Factory<?>> implements Vary {

    @Override
    public <T> Object apply(T value, Class<?> clazz, Type type) throws Exception {
        var factory = offer(clazz);
        // 创建转换函数
        @SuppressWarnings("unchecked")
        var fun = (Function<T, ?>) factory.create(type);
        //当转换的数据为空时,返回默认值,否则返回转换值
        return fun.apply(value);
    }

    /**
     * 获取数据转换工厂
     *
     * @param clazz 当前数据类型
     * @return 数据转换函数工厂
     */

    private Factory<?> offer0(Class<?> clazz) throws Exception {
        var factory = this.get(clazz);
        if (factory != null) {
            return factory;
        }
        for (var item : this) {
            if (checkAssignable(clazz, item)) {
                var fact = this.get(item);
                this.put(clazz, fact);
                return fact;
            }
        }
        throw new FactoryNotFoundException();
    }

    @Override
    protected Factory<?> offer(Type clazz) throws Exception {
        if (this.isEmpty()) {
            loader.load(Factory.class, this::put);
        }
        return offer0((Class<?>) clazz);
    }
}
