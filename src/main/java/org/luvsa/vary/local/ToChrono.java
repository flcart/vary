package org.luvsa.vary.local;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:48
 */
@Types({ChronoLocalDateTime.class, Instant.class, Date.class, Long.class})
public class ToChrono implements LProvider {

    @Override
    public Function<TemporalAccessor, ?> get(Class<?> clazz) {
        return ChronoLocalDateTime::from;
    }
}
