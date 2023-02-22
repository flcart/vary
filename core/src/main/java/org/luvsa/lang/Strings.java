package org.luvsa.lang;

import org.springframework.util.StringUtils;

import java.lang.Character.UnicodeBlock;
import java.security.MessageDigest;

/**
 * 字符串工具
 *
 * @author Dale
 * @create 2022/4/29 0:17
 */
public final class Strings {

    private Strings() {
        throw new AssertionError("No org.luvsa.vary.Strings instances for you!");
    }

    public static boolean safeEquals(String cur, String tar) {
        return MessageDigest.isEqual(cur.getBytes(), tar.getBytes());
    }

    /**
     * 小写首字母
     *
     * @param str 字符串
     * @return 小写首字母的字符串
     */
    public static String uncapitalize(String str) {
        return StringUtils.uncapitalize(str);
    }

    /**
     * 大写首字母
     *
     * @param str 字符串
     * @return 大写首字母后的字符串
     */
    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

    /**
     * 检测字符串是否全是数字(包括小数点， 即如果含有小数点，也会返回false)
     *
     * @param text 需检测的字符串
     * @return true ： 字符串为一个数字； false ： 不是数字
     */
    public static boolean isNumber(String text) {
        if (text == null || text.isBlank()) {
            return false;
        }
        // 1. 去掉正负号
        var first = text.charAt(0);
        if (first == '-' || first == '+') {
            text = text.substring(1);
        }
        if (text.isEmpty()) {
            return false;
        }
        // 小数点的位置， 如果没有 index = -1
        for (int i = 0, size = text.length(), index = text.indexOf('.'); i < size; i++) {
            if (i == index) {// 跳过小数点位置的校验
                continue;
            }
            var c = text.charAt(i);
            if (!Character.isDigit(c)) {
                // 出现非数值
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符串是否全部为汉字
     * @param text 字符串
     * @return true ： 全部为汉字字符串
     */
    public static boolean isChinese(String text) {
        return Arrays.have(Strings::isChinese, text.toCharArray());
    }

    private static boolean isChinese(char c) {
        var of = UnicodeBlock.of(c);
        return of == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || of == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || of == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || of == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || of == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || of == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || of == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return StringUtils.hasText(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isBlank(CharSequence... str) {
        return Arrays.have(Strings::isEmpty, str);
    }

    public static boolean hasBlank(CharSequence... str) {
        return Arrays.has(Strings::isEmpty, str);
    }
}
