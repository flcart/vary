package org.luvsa.vary.proxy;

import org.luvsa.reflect.Reflects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * lambda
 *
 * @author Aglet
 * @create 2022/7/1 15:03
 */
public class ParserImpl implements Parser {
    private final static String EYE = "\"";

    private final static String E = "'";

    private final Map<String, Object> variables = new HashMap<>();

    @Override
    public Function<Object, ?> create(String code) throws Exception {
        Function<Object, ?> function = null;
        for (int i = 0, size = code.length(); i < size; i++) {
            var a = code.indexOf("(", i);
            if (a < 0) {
                break;
            }
            var b = code.indexOf(")", a);
            if (b < a) {
                throw new RuntimeException(code);
            }
            var name = code.substring(i, a);
            var arg = code.substring(a + 1, b);

            var arguments = getParams(arg.split(","));

            Function<Object, ?> fun = o -> {
                var method = Reflects.findMethod(o.getClass(), name, arguments.types());
                return Reflects.invokeMethod(method, o, arguments.args());
            };

            if (function == null) {
                function = fun;
            } else {
                function = function.andThen(fun);
            }
            var index = code.indexOf(".", i);
            if (index < 0) {
                break;
            }
            i = index;
        }
        return function;
    }

    private Arguments getParams(String[] split) {
        var list = new ArrayList<>();
        var types = new ArrayList<Class<?>>();
        for (var s : split) {
            if (s.isBlank()) {
                continue;
            }
            var item = s.strip();
            var value = analyse(item);
            list.add(value);
            types.add(value.getClass());
        }
        return new Arguments(list.toArray(), types.toArray(Reflects.EMPTY_CLASS_ARRAY));
    }

    private Object analyse(String item) {
        if (match(item, EYE)) {
            return item.substring(1, item.length() - 1);
        }
        if (match(item, E)) {
            return item.substring(1, item.length() - 1).charAt(0);
        }
        return variables.get(item);
    }

    private boolean match(String item, String sign) {
        return item.startsWith(sign) && item.endsWith(sign);
    }

    @Override
    public void register(String name, Object o) {
        variables.put(name, o);
    }

    private record Arguments(Object[] args, Class<?>[] types) {
    }

}
