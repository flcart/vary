package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;


import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/11/14 15:25
 */
@Types({BigInteger.class})
public class ToBigInteger implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return i -> new BigInteger(i, 10);
    }
}
