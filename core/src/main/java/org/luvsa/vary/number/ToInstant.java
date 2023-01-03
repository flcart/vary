package org.luvsa.vary.number;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
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
public class ToInstant implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return milli -> Instant.ofEpochMilli(milli.longValue());
    }

    @Override
    public String toString() {
        return Instant.class.toString();
    }
}
