package org.luvsa.vary.chrono.zoned;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(ChronoZonedDateTime.class)
public class ZFactory extends FunctionManager<ChronoZonedDateTime<?>, ZProvider> implements Factory<ChronoZonedDateTime<?>> {

    @Override
    public Function<ChronoZonedDateTime<?>, ?> create(DataType type) {
        return  cache.computeIfAbsent(type.getClazz(), this::offer);
    }

}
