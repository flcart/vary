package org.luvsa.vary.date;

import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.Date;
import java.util.function.Function;

/**
 * {@link java.util.Date } 转换函数工厂
 *
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Date.class)
public class DFactory extends FunctionManager<Date, DProvider> implements Factory {

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

}
