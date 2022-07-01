package org.luvsa.vary.local;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/29 17:20
 */
@Types({Instant.class, Date.class, Long.class})
public class ToOther extends ToChrono implements LProvider {

    @Override
    public Function<TemporalAccessor, ?> get(Class<?> clazz) {
        return super.get(clazz).andThen(vary(clazz));
    }

}
