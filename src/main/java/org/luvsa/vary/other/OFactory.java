package org.luvsa.vary.other;

import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.Factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 字符串转换成指定数据类型的工厂
 *
 * @author Aglet
 * @create 2022/6/25 10:47
 */
public class OFactory extends AbstractFactory<Object, OProvider> implements Factory<Object> {

    private final List<OProvider> list = new ArrayList<>();

    @Override
    protected Function<Object, ?> next(Class<?> clazz) {
        for (var item : list) {
            if (item.test(clazz)) {
                return item.get(clazz);
            }
        }
        return super.next(clazz);
    }

    @Override
    protected void handle(OProvider item) {
        list.add(item);
    }

    private Map<String, Object> getValues(Object o) {
        var clazz = o.getClass();
        var map = new HashMap<String, Object>();
        while (clazz != Object.class) {
            var methods = clazz.getDeclaredMethods();
            for (var method : methods) {
                var types = method.getParameterTypes();
                if (types.length > 0) {
                    continue;
                }
                var name = Util.refer(method.getName());
                try {
                    var invoke = method.invoke(o);
                    map.put(name, invoke);
                } catch (Exception e) {
                    //
                }
            }
            clazz = clazz.getSuperclass();
        }
        return map;
    }

    private boolean fill(Object cur, Map<String, Object> map) {
        var clazz = cur.getClass();
        var entries = map.entrySet();
        var flag = false;
        for (var entry : entries) {
            var key = entry.getKey();
            var value = entry.getValue();
            var method = search(clazz, key, value);
            if (method != null) {
                try {
                    method.invoke(cur, value);
                    flag = true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    //
                }
            }
        }
        return flag;
    }

    private Method search(Class<?> clazz, String s, Object o) {
//        var aClass = o.getClass();
//        clazz.getMethod()
        return null;
    }
}
