package org.luvsa.vary.string;

import java.net.URI;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 11:16
 */
public class ToURI implements SProvider {

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return URI::create;
    }

}
