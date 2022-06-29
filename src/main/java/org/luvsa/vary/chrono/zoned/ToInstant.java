package org.luvsa.vary.chrono.zoned;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.function.Function;

/**
 * 时间戳 转 {@link Date}
 *
 * @author Aglet
 * @create 2022/6/27 13:56
 */
@Types(Instant.class)
public class ToInstant implements ZProvider {

    @Override
    public Function<ChronoZonedDateTime<?>, ?> get(Class<?> clazz) {
        return ChronoZonedDateTime::toInstant;
    }

}
