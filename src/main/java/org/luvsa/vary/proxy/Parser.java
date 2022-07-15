package org.luvsa.vary.proxy;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Aglet
 * @create 2022/7/1 15:03
 */
class Parser {

    /**
     * 代码
     */
    private final String code;

    /**
     * 变量表
     */
    private final Map<String, Object> variables;

    /**
     *
     */
    private Object base;

    private Call call;

    Parser(String code, Map<String, Object> variables) {
        this.code = code;
        this.variables = variables;
    }

    public Object get() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for (int i = 0, j = 0, size = code.length(); i < size; i++) {
            var item = code.charAt(i);
            switch (item) {
                case '.' -> {
                    var name = code.substring(j, i);
                    j = i + 1;
                    base = getVariable(name);
                }
                case '(' -> {
                    var name = code.substring(j, i);
                    j = i + 1;
                    if (base == null) {
                        throw new RuntimeException(code);
                    }
                    call = new Call(name);
                }
                case ',' -> {
                    var name = code.substring(j, i);
                    j = i + 1;
                    if (call == null || name.isBlank()) {
                        throw new RuntimeException(code);
                    }
                    var arg = variables.getOrDefault(name, name);
                    call.add(arg);
                }

                case ')' -> {
                    var name = code.substring(j, i);
                    j = i + 1;
                    if (call == null) {
                        throw new RuntimeException(code);
                    }
                    var arg = variables.getOrDefault(name, name);
                    call.add(arg);
                    base = call.invoke(base);
                }
            }
        }
        return base;
    }

    /**
     * 获取变量
     *
     * @param name 变量名称
     * @return 变量值
     */
    private Object getVariable(String name) {
        var o = variables.get(name);
        if (o == null) {
            var size = variables.size();
            if (size == 1) {
                for (Object value : variables.values()) {
                    return value;
                }
            }
            throw new IllegalArgumentException(name + " is Null!");
        }
        return o;
    }
}
