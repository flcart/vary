package org.luvsa.vary.instant;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.time.Instant;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 14:26
 */
@Types(Instant.class)
public class IFactory extends FunctionManager<Instant, IProvider> implements Factory<Instant> {

    @Override
    public Function<Instant, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }

}
