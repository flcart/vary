package org.luvsa.lang;

import java.lang.reflect.Array;
import java.util.function.Predicate;

/**
 * 数组相关的工具
 *
 * @author Aglet
 * @create 2022/7/26 11:31
 */
public final class Arrays {

    private Arrays() {
        throw new AssertionError("No org.luvsa.lang.Arrays instances for you!");
    }

    /**
     * 判断数组中是否有符合条件的元素
     *
     * @param predicate 条件
     * @param args      数组
     * @param <T>       元素的数据类型
     * @return true： 表示数组中含有符合条件的元素， false： 数组中没有符合条件的元素
     */
    @SafeVarargs
    public static <T> boolean has(Predicate<T> predicate, T... args) {
        return check(true, o -> {
            @SuppressWarnings("unchecked")
            var val = (T) o;
            return predicate.test(val);
        }, args);
    }

    /**
     * 判断 数组 中的元素是否都满足条件
     *
     * @param predicate 条件
     * @param args      数组
     * @param <T>       数组的数据类型
     * @return 数组中只要有一个不满足条件，则返回 false
     */
    @SafeVarargs
    public static <T> boolean have(Predicate<T> predicate, T... args) {
        return check(false, o -> {
            @SuppressWarnings("unchecked")
            var val = (T) o;
            return predicate.test(val);
        }, args);
    }

    public static <T> boolean has(Predicate<Character> predicate, char... args) {
        return check(true, o -> {
            if (o instanceof Character b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean have(Predicate<Character> predicate, char... args) {
        return check(false, o -> {
            if (o instanceof Character b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean has(Predicate<Integer> predicate, int... args) {
        return check(true, o -> {
            if (o instanceof Integer b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean have(Predicate<Integer> predicate, int... args) {
        return check(false, o -> {
            if (o instanceof Integer b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean has(Predicate<Float> predicate, float... args) {
        return check(true, o -> {
            if (o instanceof Float b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean have(Predicate<Float> predicate, float... args) {
        return check(false, o -> {
            if (o instanceof Float b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean has(Predicate<Double> predicate, double... args) {
        return check(true, o -> {
            if (o instanceof Double b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean have(Predicate<Double> predicate, double... args) {
        return check(false, o -> {
            if (o instanceof Double b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean has(Predicate<Boolean> predicate, boolean... args) {
        return check(true, o -> {
            if (o instanceof Boolean b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }

    public static <T> boolean have(Predicate<Boolean> predicate, boolean... args) {
        return check(false, o -> {
            if (o instanceof Boolean b) {
                return predicate.test(b);
            }
            throw new IllegalStateException();
        }, args);
    }


    /**
     * 检查数组对象中是含有元素 满足指定规则
     *
     * @param flag      true： 表示只要数组对象中含有一个元素满足指定规则，则直接返回 true； false：表示只要数组对象中含有一个元素不满足指定规则，则直接返回 false
     * @param predicate 指定规则
     * @param array     数组对象
     * @return true： 数组对象中含有元素满足指定规则
     */
    private static boolean check(boolean flag, Predicate<Object> predicate, Object array) {
        if (array == null || !array.getClass().isArray()) {
            throw new IllegalStateException();
        }
        var length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            var item = Array.get(array, i);
            var test = predicate.test(item);
            var implicit = implicit(flag, test);
            if (implicit != null) {
                return implicit;
            }
        }
        return !flag;
    }

    /**
     * @param flag true： has； false： have
     * @param test 测试结果
     * @return true： 表示隐含结果为真， false： 表示隐含结果为加， null 表示需要继续测试下一个元素
     */
    private static Boolean implicit(boolean flag, boolean test) {
        if (test) {
            if (flag) {
                // has 有一个满足条件， 直接返回 true
                return true;
            }
            // hava 需要继续测试下一个元素，
            return null;
        }
        if (flag) {
            // has， 看下一个是否满足条件， 直到出现真
            return null;
        }
        // hava 不满足条件， 直接返回 false
        return false;
    }
}
