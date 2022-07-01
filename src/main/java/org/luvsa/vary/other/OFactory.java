package org.luvsa.vary.other;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.proxy.DynamicProxy;

import java.util.function.Function;

/**
 * 字符串转换成指定数据类型的工厂
 *
 * @author Aglet
 * @create 2022/6/25 10:47
 */
public class OFactory extends FunctionManager<Object, OProvider> implements Factory<Object> {

    private final OProvider proxy = new DynamicProxy();

    @Override
    protected Function<Object, ?> next(Class<?> clazz) {
        if (clazz.isInterface()) {
            return proxy.get(clazz);
        }
        return super.next(clazz);
    }

    @Override
    public Function<Object, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }
}
