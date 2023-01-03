package org.luvsa.vary.string;

import org.luvsa.annotation.Types;

import java.io.File;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * {@link String} 转 {@link File}
 *
 * @author Aglet
 * @create 2022/7/14 11:03
 */
@Types(File.class)
public class ToFile implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return File::new;
    }

    @Override
    public String toString() {
        return String.class + " -> " + File.class + " function provider";
    }
}
