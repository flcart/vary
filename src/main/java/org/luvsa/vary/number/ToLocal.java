package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:47
 */
@Types({LocalDate.class, LocalTime.class, LocalDateTime.class})
public class ToLocal  extends ToInstant implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(found(clazz));
    }
}
