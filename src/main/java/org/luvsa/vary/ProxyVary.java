package org.luvsa.vary;

import org.luvsa.vary.other.OFactory;

import java.lang.reflect.Type;

/**
 * @author Aglet
 * @create 2022/8/4 15:56
 */
public class ProxyVary implements Vary {

    private final static Factory<Object> factory = new OFactory();

    @Override
    public <T> Object apply(T value, Type type) throws Exception {
        var function = factory.create(type);
        return function.apply(value);
    }

}
