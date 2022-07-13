package org.luvsa.vary.other;

import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 字符串转换成指定数据类型的工厂
 *
 * @author Aglet
 * @create 2022/6/25 10:47
 */
public class OFactory extends FunctionManager<Object, OProvider> implements Factory<Object> {

    /**
     * 转换为代理对象
     */
    private final OProvider proxy = new DynamicProxy();

    @Override
    protected Function<Object, ?> next(Class<?> clazz) {
        if (clazz.isInterface()) {
            return proxy.get(clazz);
        }
        // 相似转换
//        return o -> {
//            try {
//                var tar = clazz.getConstructor().newInstance();
//                if (fill(tar, getValues(o))) {
//                    return tar;
//                }
//                return next(clazz);
//            } catch (Exception e) {
//                return next(clazz);
//            }
//        };
        return super.next(clazz);
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

    @Override
    public Function<Object, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }
}
