package org.luvsa.vary.string;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/14 11:16
 */
public class ToURI implements Provider {

    @Override
    public Function<String, ?> get(Type type) {
        return URI::create;
    }

}
