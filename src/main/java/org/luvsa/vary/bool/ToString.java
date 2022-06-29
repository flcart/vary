package org.luvsa.vary.bool;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:28
 */
@Types(String.class)
public class ToString implements BProvider {

    @Override
    public Function<Boolean, ?> get(Class<?> clazz) {
        return String::valueOf;
    }

}
