package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.io.File;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 11:03
 */
@Types(File.class)
public class ToFile implements SProvider {

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return File::new;
    }

}
