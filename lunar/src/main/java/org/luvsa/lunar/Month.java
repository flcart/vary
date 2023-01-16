package org.luvsa.lunar;

import org.luvsa.lunar.Lunar.TempMonth;

/**
 * @author Aglet
 * @create 2023/1/6 13:58
 */
public class Month extends Prism {
    /**
     * 月份的中文名称
     */
    public static final char[] ARRAY = {'正', '二', '三', '四', '五', '六', '七', '八', '九', '十', '冬', '腊'};

    /**
     * 农历月份(1~12)
     */
    private final int value;

    Month(int month, int gan, int zhi) {
        super(gan, zhi);
        this.value = month;
    }

    static Month of(TempMonth month, int m, Gan gan) {
        return of(month, m, gan.index());
    }

    static Month of(TempMonth month, int m, int offset) {
        offset += offset < 5 ? 4 : -1;
        offset <<= 1;
        if (m < 0) {
            offset += 2;
        }
        return new Month(month.month(), Gan.index(m, offset), Zhi.index(m, 6));
    }

    public int getValue() {
        return Math.abs(value);
    }

    public boolean isLeap() {
        return value < 0;
    }

    @Override
    public String toString() {
        return (isLeap() ? "润" : "") + ARRAY[getValue() - 1] + '月';
    }
}
