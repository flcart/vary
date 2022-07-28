package org.luvsa.lang;

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
}
