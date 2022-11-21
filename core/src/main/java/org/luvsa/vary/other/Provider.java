package org.luvsa.vary.other;

import org.luvsa.vary.Suitable;

import java.lang.reflect.Type;

/**
 * @author Aglet
 * @create 2022/6/27 10:02
 */
public interface Provider extends org.luvsa.vary.Provider<Object>, Suitable {

    @Override
    default boolean test(Type type) {
        return false;
    }

}
