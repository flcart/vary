package org.luvsa.vary.local;

import org.luvsa.annotation.Types;
import org.luvsa.lunar.Lunar;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2023/1/9 9:25
 */
@Types(Lunar.class)
public class ToLunar implements Provider {

    @Override
    public Function<Temporal, ?> get(Type type) {
        return item -> {
            if (item instanceof LocalDate date) {
                return Lunar.from(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            }
            if (item instanceof LocalDateTime time) {
                return Lunar.from(time);
            }
            throw new UnsupportedOperationException();
        };
    }
}
