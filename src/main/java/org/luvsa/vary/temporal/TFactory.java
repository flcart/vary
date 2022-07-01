package org.luvsa.vary.temporal;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:29
 */
@Types(ZonedDateTime.class)
public class TFactory extends FunctionManager<ZonedDateTime, TProvider> implements Factory<ZonedDateTime> {
    @Override
    public Function<ZonedDateTime, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }
}
