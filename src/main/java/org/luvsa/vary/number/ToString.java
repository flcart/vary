package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 13:55
 */
@Types(String.class)
public class ToString implements Provider {
    @Override
    public Function<Number, ?> get(Type type) {
        return String::valueOf;
    }
}
