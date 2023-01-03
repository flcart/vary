package org.luvsa.vary.array;

import org.luvsa.annotation.Types;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/29 9:06
 */
@Types({Character[].class})
public class ToChars implements Provider {
    @Override
    public Function<Object, ?> get(Type type) {
        return o -> {
            var length = Array.getLength(o);
            var array = new Character[length];
            for (int i = 0; i < length; i++) {
                var item = Array.get(o, i);
                if (item instanceof Character character) {
                    array[i] = character;
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            return array;
        };
    }
}
