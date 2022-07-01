package org.luvsa.vary.local;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class LFactory extends FunctionManager<TemporalAccessor, LProvider> implements Factory<TemporalAccessor> {

    @Override
    public Function<TemporalAccessor, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }

}
