package org.luvsa.vary.temporal;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:37
 */
@Types(LocalTime.class)
public class ToLocalTime implements TProvider {
    @Override
    public Function<ZonedDateTime, ?> get(Class<?> clazz) {
        return ZonedDateTime::toLocalTime;
    }
}
