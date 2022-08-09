package org.luvsa.vary;

import org.luvsa.exception.FactoryNotFoundException;
import org.luvsa.reflect.Reflections;

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
    public <T> Object apply(T value, Type type) throws Exception {
        //如果值为空,直接返回默认值
        if (value == null) {
            return null;
        }
        //获取当前数据的Class对象
        var clz = Reflections.wrap(value.getClass());
        if (checkAssignable(clz, type)) {
            // 当前数据与目标数据存在继承或者实现关系
            return value;
        }
        var factory = offer(clz);
        // 创建转换函数
        @SuppressWarnings("unchecked")
        var fun = (Function<T, ?>) factory.create(type);
        //当转换的数据为空时,返回默认值,否则返回转换值
        return fun.apply(value);
    }

    /**
     * 判断当前 {@link Class 数据类型} 与 目标数据类型是否存在 继承 或者实现关系
     *
     * @param clz  当前数据的 Class 对象
     * @param type 目标数据 类型
     * @return true：当前数据 为目标数据类型的一个实例
     */
    private boolean checkAssignable(Class<?> clz, Type type) {
        if (type instanceof Class<?> cls) {
            if (cls == clz) {
                return true;
            }
            var wrap = Reflections.wrap(cls);
            return wrap.isAssignableFrom(clz);
        }
        return false;
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
