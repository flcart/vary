package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

/**
 * 时间戳 转 {@link Date}
 *
 * @author Aglet
 * @create 2022/6/27 13:56
 */
@Types(Instant.class)
public class ToInstant implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return milli -> Instant.ofEpochMilli(milli.longValue());
    }

}
