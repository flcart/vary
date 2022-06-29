package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 16:07
 */
@Types(Long.class)
public class ToLong implements IProvider {

    @Override
    public Function<Instant, ?> get(Class<?> clazz) {
        return Instant::toEpochMilli;
    }

}
