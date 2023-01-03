package org.luvsa.vary.number;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 15:03
 */
@Types({Character.class})
public class ToChar implements Provider {

    @Override
    public Function<Number, ?> get(Type type) {
        return num -> (char) num.intValue();
    }

    @Override
    public String toString() {
        return Character.class.toString();
    }
}
