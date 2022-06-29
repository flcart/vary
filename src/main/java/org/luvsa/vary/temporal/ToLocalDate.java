package org.luvsa.vary.temporal;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:36
 */
@Types(LocalDate.class)
public class ToLocalDate implements TProvider {

    @Override
    public Function<ZonedDateTime, ?> get(Class<?> clazz) {
        return ZonedDateTime::toLocalDate;
    }

}
