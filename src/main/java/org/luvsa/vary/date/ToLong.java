package org.luvsa.vary.date;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 17:02
 */
@Types(Long.class)
public class ToLong implements DProvider {

    @Override
    public Function<Date, ?> get(Class<?> clazz) {
        return Date::getTime;
    }

}
