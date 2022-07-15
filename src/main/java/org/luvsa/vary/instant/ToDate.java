package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:36
 */
@Types(Date.class)
public class ToDate implements IProvider {

    @Override
    public Function<Instant, ?> get(Type type) {
        return Date::from;
    }
}
