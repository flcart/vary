package org.luvsa.vary.array;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/18 11:11
 */
@Types(Object[].class)
public class ToObject implements Provider {
    @Override
    public Function<Object, ?> get(Type type) {
        return o -> {
            var length = Array.getLength(o);
            var array = new Object[length];
            for (int i = 0; i < length; i++) {
                array[i] = Array.get(o, i);
            }
            return array;
        };
    }
}
