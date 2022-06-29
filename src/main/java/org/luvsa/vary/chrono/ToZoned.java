package org.luvsa.vary.chrono;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 15:59
 */
@Types(ChronoZonedDateTime.class)
public class ToZoned implements CProvider {

    @Override
    public Function<ChronoLocalDateTime<?>, ?> get(Class<?> clazz) {
        return item -> item.atZone(ZoneId.systemDefault());
    }

}
