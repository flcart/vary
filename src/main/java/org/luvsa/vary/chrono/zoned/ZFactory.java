package org.luvsa.vary.chrono.zoned;

import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Function;

/**
 *  {@link ChronoZonedDateTime 时区日期} 转换函数工厂
 *
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoZonedDateTime.class)
public class ZFactory extends FunctionManager<ChronoZonedDateTime<?>, ZProvider> implements Factory {

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> create(Class<R> clazz) {
        return (Function<T, R>) cache.computeIfAbsent(clazz, this::offer);
    }

}
