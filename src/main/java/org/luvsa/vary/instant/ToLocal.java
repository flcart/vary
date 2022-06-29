package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

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
public class ToLocal extends ToZoned implements IProvider {

    @Override
    public Function<Instant, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(next(clazz));
    }

}
