package org.luvsa.lunar;

import org.luvsa.lunar.Lunar.TempYear;

/**
 * @author Aglet
 * @create 2023/1/6 13:58
 */
public class Day extends Prism{

    /**
     * 阴历日
     */
    public static final String[] ARRAY = {"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一",
            "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "廿十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六",
            "廿七", "廿八", "廿九", "三十"};

    /**
     * 偏差值
     */
    private static final int OFFSET = -8;

    /**
     * 农历日期(1~ 30)
     */
    private final int value;

    Day(int day, int gan, int zhi) {
        super(gan, zhi);
        this.value = day;
    }

    public static Day of(double julian) {
        var date = Lunar.toDateTime(julian);
        var year = TempYear.of(date.getYear());
        var month = year.months[0];
        for (int i = 1; i < year.months.length; i++) {
            var item = year.months[i];
            if (item.julian() > julian) {
                break;
            }
            month = item;
        }
        var value = (int) (julian - month.julian() + 1.5);
        var start = (int) (julian + 1.5);
        return new Day(value, Gan.index(start, OFFSET), Zhi.index(start, OFFSET));
    }

    public static Day of(double julian, int day) {
        var start = (int) (julian + day + 0.5);
        return new Day(day, Gan.index(start, OFFSET), Zhi.index(start, OFFSET));
    }

    /**
     * @return 阴历日期 数值
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ARRAY[value - 1];
    }
}
