package org.luvsa.vary.number;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 15:03
 */
@Types({char.class, Character.class})
public class ToChar implements NProvider {

    @Override
    public Function<Number, ?> get(Class<?> clazz) {
        return num -> (char) num.intValue();
    }

}
