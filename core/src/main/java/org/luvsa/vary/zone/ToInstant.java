package org.luvsa.vary.zone;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Function;

/**
 * {@link ChronoZonedDateTime} 转 {@link Instant}
 *
 * @author Aglet
 * @create 2022/6/27 13:56
 */
@Types(Instant.class)
public class ToInstant implements Provider {

    @Override
    public Function<ZonedDateTime, ?> get(Type type) {
        return ChronoZonedDateTime::toInstant;
    }
}
