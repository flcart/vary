package org.luvsa.lang;

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

    public static <T> boolean has(Predicate<T> predicate, T... args){
        return has1(true, predicate, args);
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
        return has1(false, predicate, args);
    }

    /**
     * 检查数组对象中是含有元素 满足指定规则
     *
     * @param flag      true： 表示只要数组对象中含有一个元素满足指定规则，则直接返回 true； false：表示只要数组对象中含有一个元素不满足指定规则，则直接返回 false
     * @param predicate 指定规则
     * @param args      数组对象
     * @param <T>       数组数据类型
     * @return true： 数组对象中含有元素满足指定规则
     */
    private static <T> boolean has1(boolean flag, Predicate<T> predicate, T[] args) {
        for (var item : args) {
            var test = predicate.test(item);
            if (flag) {
                if (test) {
                    return true;
                }
            } else if (!test) {
                return false;
            }
        }
        return !flag;
    }


    private static boolean has0(boolean flag, CharPredicate predicate, char[] args) {
        for (char item : args) {
            var test = predicate.test(item);
            if (test) {
                if (flag) {
                    // has 有一个满足条件， 直接返回 true
                    return true;
                }
                // hava 需要所有的元素都满足条件
                continue;
            }

            if (flag) {
                // has， 看下一个是否满足条件
                continue;
            }

            // hava 不满足条件， 直接返回 false
            return false;
        }
        return !flag;
    }

    static boolean have0(CharPredicate predicate, char[] array) {
        return has0(false, predicate, array);
    }

    static boolean has0(CharPredicate predicate, char[] array) {
        return has0(true, predicate, array);
    }
}
