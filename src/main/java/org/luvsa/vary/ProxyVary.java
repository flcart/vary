package org.luvsa.vary;

import org.luvsa.vary.other.Factory;

import java.lang.reflect.Type;

/**
 * @author Aglet
 * @create 2022/8/4 15:56
 */
public class ProxyVary implements Vary {

    private final static org.luvsa.vary.Factory<Object> factory = new Factory();

    @Override
    public boolean enabled() {
        // 加载配置文件
        var resource = Thread.currentThread().getContextClassLoader().getResource("vary.proxy");
        return resource != null;
    }

    @Override
    public <T> Object apply(T value, Class<?> clazz, Type type) throws Exception {
        return factory.create(type).apply(value);
    }
}
