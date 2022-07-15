package org.luvsa.vary.chrono;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.function.Function;

/**
 * {@link ChronoLocalDateTime} 转 {@link Date} 和 {@link Long 时间戳}
 *
 * @author Aglet
 * @create 2022/6/27 16:24
 */
@Types({Instant.class, Date.class, Long.class})
public class ToOther extends ToZoned implements CProvider {

    @Override
    public Function<ChronoLocalDateTime<?>, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

}
