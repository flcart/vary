package org.luvsa.vary.string;

import org.luvsa.annotation.Types;
import org.luvsa.vary.Vary;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * {@link String 字符串} 转 {@link Character 字符数组}, 使用案例: <pre>{@code
 *  char[] array = Vary.change("char", char[].class);
 * }</pre>
 *
 * @author Aglet
 * @create 2022/7/15 14:05
 */
@Types({char[].class, Character[].class})
public class ToChars implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return s -> {
            var chars = s.strip().toCharArray();
            if (type == chars.getClass()) {
                return chars;
            }
            return Vary.convert(chars, type);
        };
    }

    @Override
    public String toString() {
        return String.class + " -> [" + char[].class + ", " + Character[].class + "] function provider";
    }
}
