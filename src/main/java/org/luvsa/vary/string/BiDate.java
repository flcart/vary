package org.luvsa.vary.string;

/**
 * @author Aglet
 * @create 2022/6/28 9:04
 */
abstract class BiDate<T> {

    /**
     * 默认支持的日期格式
     */
    final char[] ARRAY_DATE = {'y', 'M', 'd'};

    /**
     * 默认支持的时间格式
     */
    final char[] ARRAY_TIME = {'H', 'm', 's', 'S'};

    final char[] _DATE_TIME = {'y', 'M', 'd', 'H', 'm', 's', 'S'};

    /**
     * 创建一个合适的时间格式 format
     *
     * @param array 数组， 组要元素{@link #ARRAY_DATE} 和{@link #ARRAY_TIME}
     * @param text  待格式化的 时间字符串
     * @return 一个时间 format 字符串
     */
    String format(char[] array, String text) {
        var chars = text.toCharArray();
        var builder = new StringBuilder();
        for (int i = 0, j = 0; i < chars.length && j < array.length; i++) {
            var aChar = chars[i];
            if (Character.isDigit(aChar)) {
                builder.append(array[j]);
            } else {
                if (aChar != ' ') {
                    j++;
                }
                builder.append(aChar);
            }
        }
        return builder.toString();
    }

    T next(String txt, char[] chars) {
        if (txt.isBlank()) {
            return null;
        }

        var value = txt.trim();
        var format = format(chars, value).trim();

        if (format.isBlank()) {
            //不支持
            throw new IllegalArgumentException("Unable to convert " + value + " to LocalDate");
        }

        return next(txt, format);
    }

    abstract T next(String value, String format);
}
