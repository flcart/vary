package org.luvsa.vary.instant;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:33
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class ToLocal extends ToZoned implements Provider {

    @Override
    public Function<Instant, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

}
