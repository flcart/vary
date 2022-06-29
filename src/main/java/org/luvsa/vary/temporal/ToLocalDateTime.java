package org.luvsa.vary.temporal;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:24
 */
@Types(LocalDateTime.class)
public class ToLocalDateTime implements TProvider {

    @Override
    public Function<ZonedDateTime, ?> get(Class<?> clazz) {
        return ZonedDateTime::toLocalDateTime;
    }
}
