package org.luvsa.lunar;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2023/1/6 13:57
 */
public class Year extends Prism {

    /**
     * 中文映射表
     */
    public static final char[] CHINESE = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    private final int value;

    public Year(int value) {
        super(value, value);
        this.value = value;
    }

    public static Year of(int year) {
        return new Year(year);
    }

    public int getValue() {
        return value;
    }

    void reset(int offset) {
        this.gan = gan.next(offset);
        this.zhi = zhi.next(offset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var year = (Year) o;
        return value == year.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public static String format(int year) {
        var array = String.valueOf(year).toCharArray();
        var builder = new StringBuilder();
        for (var c : array) {
            builder.append(CHINESE[c - 48]);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return format(this.value) + '年';
    }
}
