package org.luvsa.io;

import java.io.InputStream;

/**
 * @author Aglet
 * @create 2023/1/11 9:52
 */
public class Resources {

    public static InputStream asStream(String name) {
        var loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(name);
    }
}
