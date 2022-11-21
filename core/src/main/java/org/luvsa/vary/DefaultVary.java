package org.luvsa.vary;

import org.luvsa.exception.FactoryNotFoundException;

import java.lang.reflect.Type;
import java.util.NoSuchElementException;
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
        if (this.isEmpty()) {
            // 1. 加载
            loader.load(Factory.class, this::put);
        }
        try {
            return this.get(clazz, factory -> next(factory, value, type));
        } catch (NoSuchElementException e) {
            for (var item : this) {
                if (checkAssignable(clazz, item)) {
                    return this.get(item, factory -> {
                        this.put(clazz, factory);
                        return next(factory, value, type);
                    });
                }
            }
            throw new FactoryNotFoundException();
        }
    }

    private <T> Object next(Factory<?> factory, T value, Type type) {
        @SuppressWarnings("unchecked")
        var fun = (Function<T, ?>) factory.create(type);
        //当转换的数据为空时,返回默认值,否则返回转换值
        return fun.apply(value);
    }
}
