package org.luvsa.vary.bool;

import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * {@link Boolean bool} 类型数据转换函数工厂
 *
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({Boolean.class, boolean.class})
public class BFactory extends FunctionManager<Boolean, BProvider> implements Factory {

    /**
     * 创建 {@link Boolean bool} 类型数据转目标类型数据的函数
     *
     * @param clazz 目标数据类型
     * @param <T>   {@link Boolean bool}
     * @param <R>   目标数据类型
     * @return 转换函数
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

}
