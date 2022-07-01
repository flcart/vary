package org.luvsa.vary.date;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Date.class)
public class DFactory extends FunctionManager<Date, DProvider> implements Factory<Date> {


    @Override
    public Function<Date, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }
}
