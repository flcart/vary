package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/15 14:05
 */
@Types({char[].class, Character[].class})
public class ToChars implements SProvider {
    @Override
    public Function<String, ?> get(Type type) {
        return s -> s.strip().toCharArray();
    }
}
