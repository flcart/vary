package org.luvsa.vary.zone;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:37
 */
@Types(LocalTime.class)
public class ToLocalTime implements Provider {
    @Override
    public Function<ZonedDateTime, ?> get(Type type) {
        return ZonedDateTime::toLocalTime;
    }
}
