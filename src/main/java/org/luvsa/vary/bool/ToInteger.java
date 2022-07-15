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
@Types({Integer.class, int.class})
public class ToInteger implements BProvider {

    @Override
    public Function<Boolean, ?> get(Type type) {
        return flag -> flag ? 0 : 1;
    }
}
