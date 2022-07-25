package org.luvsa.vary.zone;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:36
 */
@Types(LocalDate.class)
public class ToLocalDate implements Provider {
    @Override
    public Function<ZonedDateTime, ?> get(Type type) {
        return ZonedDateTime::toLocalDate;
    }
}
