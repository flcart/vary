package org.luvsa.vary.string;

import org.luvsa.vary.TypeSupplier.Types;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/6/27 10:02
 */

@Types(BigDecimal.class)
public class ToDecimal implements SProvider {

    /**
     * 中文数字表
     */
    private static final char[] TABLE = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九',
            '十', '百', '千', '万', '亿'};

    private static final BigDecimal ZERO = new BigDecimal(0);

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return s -> {
            if (s.isBlank()) {
                return null;
            }
            if (hasChinese(s)) {
                // 解析
                return new Parser(s).get();
            }
            return new BigDecimal(s);
        };
    }

    /**
     * 中文数字解析器
     */
    static class Parser {
        /**
         * 中文 数字
         */
        private final String txt;

        /**
         * 起始累加值
         */
        private BigDecimal decimal = ZERO;

        Parser(String txt) {
            this.txt = txt;
        }

        public BigDecimal get() {
            for (int i = 0, size = txt.length(); i < size; i++) {
                var c = txt.charAt(i);
                var value = value(c);
                if (c == '零' || c == '十' || c == '百' || c == '千' || c == '万' || c == '亿') {
                    decimal = merge(value);
                    continue;
                }
                var target = new BigDecimal(value);
                var u = i + 1 < size ? txt.charAt(++i) : '零';
                var unit = value(u);
                decimal = merge(target, unit);
                if (u == '万' || u == '亿') {
                    var sub = txt.substring(i + 1);
                    var parser = new Parser(sub);
                    return decimal.add(parser.get());
                }
            }
            return decimal;
        }

        private BigDecimal merge(BigDecimal decimal, double unit) {
            if (unit == 0 || unit == 10 || unit == 100 || unit == 1000) {
                var decimal1 = merge0(decimal, unit);
                return this.decimal.add(decimal1);
            }
            if (unit == 10000 || unit == 100000000) {
                return merge0(this.decimal.add(decimal), unit);
            }
            throw new IllegalArgumentException("数据单位错误： " + unit);
        }

        /**
         * 合并
         *
         * @param value 值
         * @return 合并结果
         */
        private BigDecimal merge(double value) {
            return merge0(decimal, value);
        }

        private BigDecimal merge0(BigDecimal decimal, double unit) {
            if (decimal.equals(ZERO)) {
                return new BigDecimal(unit);
            }

            if (unit == 0) {
                return decimal;
            }

            var temp = new BigDecimal(unit);
            return decimal.multiply(temp);
        }
    }

    private static double value(char item) {
        var index = index(item);
        if (index < 10) {
            return index;
        }
        var times = index - 9;
        if (times == 5) {
            return 100000000;
        }
        return Math.pow(10, times);
    }

    private static int index(char c) {
        for (int i = 0, size = TABLE.length; i < size; i++) {
            if (c == TABLE[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private static boolean hasChinese(String s) {
        for (var c : TABLE) {
            var index = s.indexOf(c);
            if (index >= 0) {
                return true;
            }
        }
        return false;
    }
}
