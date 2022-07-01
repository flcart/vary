package org.luvsa.vary.chrono;


import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Function;

/**
 *  {@link ChronoLocalDateTime 日期} 转换函数工厂
 *
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoLocalDateTime.class)
public class CFactory extends FunctionManager<ChronoLocalDateTime<?>, CProvider> implements Factory {
    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

}
