package org.luvsa.vary;

import org.luvsa.vary.other.OFactory;

import java.lang.reflect.Proxy;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * 默认转换器实现入口
 *
 * @author Aglet
 * @create 2022/6/29 16:50
 */
public class DefaultVary extends Manager<Factory<?>> implements Vary {
    /**
     * 不支持类型的默认转换函数工厂
     */
    private static final Factory<?> DEFAULT = new OFactory();

    /**
     * 采取单列模式
     */
    static final Vary INSTANCE = new DefaultVary();

    /**
     * 私有化构造函数
     */
    private DefaultVary() {
    }

    @Override
    public <T> Object apply(T value, DataType type) {
        //如果值为空,直接返回默认值
        if (value == null) {
            return null;
        }

        //获取当前数据的Class对象
        var clz = value.getClass();
        if (type.isAssignableFrom(clz)) {
            // 当前数据与目标数据存在继承或者实现关系
            return value;
        }

        try {
            // 查找数据转换工厂 对应的类型转换器
            return next(offer(clz), value, type);
        } catch (Exception e) {
            return next(DEFAULT, value, type);
        }
    }

    private <T> Object next(Factory<?> factory, T value, DataType type) {
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
    @Override
    protected Factory<?> offer(Class<?> clazz) throws Exception {
        //初始化管理器
        if (cache.isEmpty()) {
            // 加载 转换器工厂
            synchronized (cache) {
                if (cache.isEmpty()) {
                    load(Factory.class, cache::put);
                }
            }
        }

        var factory = cache.get(clazz);
        if (factory != null) {
            return factory;
        }

        if (Proxy.class.isAssignableFrom(clazz)) {
            // 动态代理问题， 会取第一个接口， 如果是多个接口，则会有问题
            var interfaces = clazz.getInterfaces();
            var joiner = new StringJoiner("\n");
            for (var item : interfaces) {
                var offer = offer(item);
                if (offer != null) {
                    // 保存下
                    cache.put(clazz, offer);
                    return offer;
                }
                joiner.add(item.getName());
            }
            throw new FactoryNotFoundException(joiner.toString());
        }

        var set = cache.keySet();
        for (var item : set) {
            if (item.isAssignableFrom(clazz)) {
                var fact = cache.get(item);
                cache.put(clazz, fact);
                return fact;
            }
        }
        throw new FactoryNotFoundException();
    }
}
