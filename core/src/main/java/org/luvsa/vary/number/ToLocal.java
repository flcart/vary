package org.luvsa.vary.number;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:47
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class ToLocal extends ToInstant implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

    @Override
    public String toString() {
        return Arrays.toString(new Class<?>[]{LocalDate.class, LocalTime.class, LocalDateTime.class});
    }
}
