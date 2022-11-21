package org.luvsa.vary;

import org.junit.jupiter.api.Test;
import org.luvsa.reflect.Reflects;
import org.luvsa.vary.proxy.Lambda;
import org.luvsa.vary.proxy.ParserImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * @author Dale
 * @create 2022/7/3 12:49
 */
class GenericsTest {

    @Test
    void accept() {
//        var list = new ArrayList<Map<String, List<String>>>() {
//        };
//        var clazz = list.getClass();
//        Generics.accept(clazz, 0, aClass -> {
//            System.out.println(aClass);
//        });


    }

    @Test
    void type() {
        var clazz = Future.class;
        Reflects.doWithMethods(clazz, method -> {
//            var type = DataType.of(method.getGenericReturnType());
//            System.out.println(type);
        });
    }

    @Test
    void lambda(){
        var o =  Map.of("key", "122001");
        Function<String, Type> function = Type::of;

        Function<Object, ?> fun0 = o1 -> new ArrayList<>();

        var parser = new Lambda();
        parser.register(Type.class);
        var fun = parser.create("Type::of");
        var apply = fun.apply("大吉");
        System.out.println(apply);
    }


    @Test
    void parser() throws Exception {
        var parser = new ParserImpl();
//        var s = " 11111.1 ";
//        var split = s.strip().split(".");

        var function = parser.create("strip().split(\";\")");
        var apply = function.apply("15;22;158;164");
        if (apply.getClass().isArray()){
            var joiner = new StringJoiner(", ");
            var length = Array.getLength(apply);
            for (int i = 0; i < length; i++) {
                joiner.add(Array.get(apply, i).toString());
            }
            System.out.println(joiner);
        } else {
            System.out.println(apply);
        }
    }

    public enum Type {
        /**
         * 大吉
         */
        GOOD_LUCKY("大吉"),

        /**
         * 吉
         */
        LUCKY("吉"),

        /**
         * 半吉
         */
        HALF_LUCKY("半吉"),

        /**
         * 凶
         */
        OMINOUS("凶");

        private final String value;

        Type(String name) {
            this.value = name;
        }
        public static Type of(String name){
            var types = values();
            for (var type : types) {
                if (Objects.equals(name, type.value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No type named :" + name);
        }
    }
}