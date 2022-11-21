package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:07
 */
@Types({Long.class})
public class ToLong implements Provider {

    @Override
    public Function<Instant, ?> get(Type type) {
        return Instant::toEpochMilli;
    }

}
