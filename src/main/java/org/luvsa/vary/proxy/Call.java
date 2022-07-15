package org.luvsa.vary.proxy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aglet
 * @create 2022/7/1 15:04
 */
class Call {
    private final String name;

    private final List<Object> list = new ArrayList<>();

    Call(String name) {
        this.name = name;
    }

    public void add(Object o) {
        if (o == null) {
            return;
        }
        if (o instanceof String s) {
            if (s.isBlank()) {
                return;
            }
            if (s.startsWith("\"") && s.endsWith("\"")) {
                o = s.substring(1, s.length() - 1);
            }
        }
        list.add(o);
    }

    public Object invoke(Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        var size = list.size();
        var args = new Object[size];
        var types = new Class<?>[size];
        for (int i = 0; i < size; i++) {
            var item = list.get(i);
            args[i] = item;
            types[i] = item.getClass();
        }
        var aClass = o.getClass();
        var method = aClass.getMethod(name, types);
        return method.invoke(o, args);
    }

}
