package org.luvsa.vary.proxy;

import org.luvsa.vary.other.OProvider;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/15 10:07
 */
public class NoParamProxy implements OProvider {

    @Override
    public Function<Object, ?> get(Class<?> clazz) {
        return o -> Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new NoParamInvocationHandler(o));
    }

    @Override
    public boolean test(Class<?> clazz) {
        return clazz.isInterface();
    }

    private record NoParamInvocationHandler(Object source) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }

    }
}
