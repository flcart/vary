package org.luvsa.vary.bool;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * {@link Boolean bool} 转 {@link Integer int}
 *
 * @author Aglet
 * @create 2022/6/28 9:30
 */
@Types({Integer.class})
public class ToInteger implements Provider {

    @Override
    public Function<Boolean, ?> get(Type type) {
        return flag -> flag ? 0 : 1;
    }

    @Override
    public String toString() {
        return "Boolean-Integer-Provider";
    }
}
