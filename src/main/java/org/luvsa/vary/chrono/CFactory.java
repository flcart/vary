package org.luvsa.vary.chrono;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoLocalDateTime.class)
public class CFactory extends FunctionManager<ChronoLocalDateTime<?>, CProvider> implements Factory<ChronoLocalDateTime<?>> {

    @Override
    public Function<ChronoLocalDateTime<?>, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }
}
