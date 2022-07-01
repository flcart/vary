package org.luvsa.vary.bool;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types({Boolean.class, boolean.class})
public class BFactory extends FunctionManager<Boolean, BProvider> implements Factory<Boolean> {

    @Override
    public Function<Boolean, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }

}
