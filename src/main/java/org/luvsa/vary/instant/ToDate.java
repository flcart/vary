package org.luvsa.vary.instant;

import org.luvsa.vary.TypeSupplier.Types;

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
    public Function<Instant, ?> get(Class<?> clazz) {
        return Date::from;
    }

}
