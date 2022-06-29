package org.luvsa.vary.bool;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/28 9:30
 */
@Types({Integer.class, int.class})
public class ToInteger implements BProvider {

    @Override
    public Function<Boolean, ?> get(Class<?> clazz) {
        return flag -> flag ? 0 : 1;
    }

}
