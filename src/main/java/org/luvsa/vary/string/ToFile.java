package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.io.File;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 11:03
 */
@Types(File.class)
public class ToFile implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return File::new;
    }

}
