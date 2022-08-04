package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 17:24
 */
@Types({Byte.class,  Short.class, Integer.class, Long.class,  Float.class, Double.class,  BigInteger.class})
public class ToNumber extends ToDecimal implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return super.get(type).andThen(found(type));
    }

}
