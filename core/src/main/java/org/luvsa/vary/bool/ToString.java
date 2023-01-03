package org.luvsa.vary.bool;

import org.luvsa.annotation.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * {@link Boolean bool} 转 {@link String 字符串}
 *
 * @author Aglet
 * @create 2022/6/28 9:28
 */
@Types(String.class)
public class ToString implements Provider {

    @Override
    public Function<Boolean, ?> get(Type type) {
        return String::valueOf;
    }

    @Override
    public String toString() {
        return "Boolean-String-Provider";
    }
}
