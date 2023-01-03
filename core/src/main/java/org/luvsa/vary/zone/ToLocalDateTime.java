package org.luvsa.vary.zone;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:24
 */
@Types(LocalDateTime.class)
public class ToLocalDateTime implements Provider {

    @Override
    public Function<ZonedDateTime, ?> get(Type type) {
        return ZonedDateTime::toLocalDateTime;
    }
}
