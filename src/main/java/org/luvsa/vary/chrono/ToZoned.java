package org.luvsa.vary.chrono;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.function.Function;

/**
 *  {@link ChronoLocalDateTime} 转 {@link ChronoZonedDateTime}
 *
 * @author Aglet
 * @create 2022/6/27 15:59
 */
@Types(ChronoZonedDateTime.class)
public class ToZoned implements CProvider {

    @Override
    public Function<ChronoLocalDateTime<?>, ?> get(Type type) {
        return item -> item.atZone(ZoneId.systemDefault());
    }

}
