package org.luvsa.lunar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 阴历
 *
 * @author Aglet
 * @create 2023/1/6 13:57
 */
public class Lunar {

    static final int HOURS_PER_DAY = 24;
    static final int SECONDS_PER_MINUTE = 60;
    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

    /**
     * 2000年1月1日 12:00:00 的儒略日数
     */
    public static final double J2000 = 2451545;

    /**
     * 24 农历节气(以当前节气数组为标准， 其双数为节，也称之为节令， 单数为气)
     */
    public static final String[] TERMS = {"冬至", "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪"};

    /**
     * 年柱
     */
    Year year;

    /**
     * 月柱
     */
    Month month;

    /**
     * 日柱
     */
    Day day;

    /**
     * 时柱
     */
    Time time;

    /**
     * 阳历日期
     */
    final LocalDateTime dateTime;

    /**
     * 临时 年变量
     */
    final TempYear tempYear;

    /**
     * 临时月对象
     */
    final TempMonth tempMonth;

    public static Lunar from(int year, int month, int day) {
        return from(year, month, day, 0, 0);
    }

    /**
     * 构造 Lunar 日期实例
     *
     * @param year   阳历年
     * @param month  阳历月
     * @param day    阳历日
     * @param hour   时
     * @param minute 分
     * @return 阴历实例
     */
    public static Lunar from(int year, int month, int day, int hour, int minute) {
        return from(year, month, day, hour, minute, 0);
    }

    /**
     * 构造 Lunar 日期实例
     *
     * @param year   阳历年
     * @param month  阳历月
     * @param day    阳历日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 阴历实例
     */
    public static Lunar from(int year, int month, int day, int hour, int minute, int second) {
        return new Lunar(LocalDateTime.of(year, month, day, hour, minute, second));
    }

    public static Lunar from(LocalDateTime dateTime) {
        return new Lunar(dateTime);
    }

    public static Lunar now() {
        return new Lunar(LocalDateTime.now());
    }

    Lunar(LocalDateTime dateTime) {
        tempYear = TempYear.of(dateTime.getYear());
        var date = dateTime.toLocalDate();
        int y = 0, m = 0, d = 0;
        var current = date.toEpochDay();
        TempMonth tempMonth = null;
        for (var month : tempYear.months) {
            // 这个月初一
            var first = month.getLocalDate();
            var days = current - first.toEpochDay();
            if (days < month.days) {
                y = month.year;
                m = month.month;
                d = (int) days + 1;
                tempMonth = month;
                break;
            }
        }
        this.dateTime = dateTime;
        this.tempMonth = tempMonth;
        init(y, d);
    }

    /**
     * 初始化
     */
    private void init(int year, int day) {
        //以立春作为新一年的开始的干支纪年
        this.year = Year.of(year);

        var solarYear = this.dateTime.getYear();
        var lunarYear = this.year.getValue();

        //获取立春的阳历时刻，
        var chun = tempYear.next("立春");
        if (chun.getYear() != solarYear) {
            chun = tempYear.next(chun, "立春");
        }

        //阳历和阴历年份相同代表正月初一及以后
        if (lunarYear == solarYear) {//阴历年 == 阳历年
            if (this.dateTime.isBefore(chun)) { // 年后立春
                this.year.reset(-1);
            }
        } else if (lunarYear < solarYear) { //阴历年 < 阳历年, 一般在年前出现这种情况
            if (!this.dateTime.isBefore(chun)) {// 年前立春
                this.year.reset(1);
            }
        }

        //序号：大雪以前-3，大雪到小寒之间-2，小寒到立春之间-1，立春之后0
        int index = -3;
        LocalDateTime start = null, end;
        for (int i = 0, j = 23; i < 31; i += 2, j += 2) {
            // 当前月的节令
            var begin = null == start ? this.dateTime : start;
            //下个月的节令
            end = tempYear.terms[i];
            // 月初 <= date < 下个月初,   <=>  当前月的节令 <= birth < 下个月的节令
            if (!this.dateTime.isBefore(begin) && this.dateTime.isBefore(end)) {
                break;
            }
            start = end;
            index++;
        }
        //干偏移值（以立春当天起算）
        this.month = Month.of(tempMonth, index, this.year.gan);
        this.day = Day.of(tempMonth.julian, day);
        this.time = Time.of(this.day.gan, this.dateTime.toLocalTime());
    }

    /**
     * 儒略日 转换成 日期时间({@link LocalDateTime 公历日期时间})
     *
     * @param julian 儒略日
     * @return 日期时间
     */
    public static LocalDateTime toDateTime(double julian) {
        // 和公历从 0点开始一致
        var j = julian + 0.5;
        // 整数部分，天数
        var z = Math.floor(j);
        // 小数部分，时刻
        var f = j - z;
        var date = toDate(z);
        var time = toTime(f);
        return LocalDateTime.of(date, time);
    }

    /**
     * 儒略日转日期({@link LocalTime 公历日期})
     *
     * @param julian 儒略日
     * @return 日期
     */
    private static LocalDate toDate(double julian) {
        // 1582年10月15日0时前，岁长365.25， 此后适用格里历，岁长365.2425
        if (julian >= 2299161) {
            var a = Math.floor((julian - 1867216.25) / 36524.25);
            julian += 1 + a - Math.floor(a / 4);
        }
        julian += 1524;
        var year = Math.floor((julian - 122.1) / 365.25);
        julian -= Math.floor(365.25 * year);
        var month = Math.floor(julian / 30.6001);
        var day = julian - Math.floor(30.6001 * month);
        if (month > 13) {
            month -= 13;
            year -= 4715;
        } else {
            month -= 1;
            year -= 4716;
        }
        return LocalDate.of((int) year, (int) month, (int) day);
    }

    /**
     * 儒略日转时间({@link LocalTime 公历时间})
     *
     * @param julian 儒略日
     * @return 时间
     */
    private static LocalTime toTime(double julian) {
        if (julian >= 1 || julian <= -1) {
            julian -= Math.floor(julian);
        }
        julian *= 24;
        var hour = Math.floor(julian);
        julian -= hour;
        julian *= 60;
        var minute = Math.floor(julian);
        julian -= minute;
        julian *= 60;
        var second = Math.round(julian);
        if (second > 59) {
            second -= 60;
            minute++;
        }
        if (minute > 59) {
            minute -= 60;
            hour++;
        }
        return LocalTime.of((int) hour, (int) minute, (int) second);
    }

    @Override
    public String toString() {
        return year + " " + month + " " + day + " " + time;
    }

    /**
     * 临时存储年的推算数据， 比如当前前所有月的信息， 以及当前年节气时间等数据
     */
    public static final class TempYear {
        /**
         * 闰冬月年份
         */
        private static final int[] LEAP_11 = {75, 94, 170, 238, 265, 322, 389, 469, 553, 583, 610, 678, 735, 754, 773, 849, 887, 936, 1050, 1069, 1126, 1145, 1164, 1183, 1259, 1278, 1308, 1373, 1403, 1441, 1460, 1498, 1555, 1593, 1612, 1631, 1642, 2033, 2128, 2147, 2242, 2614, 2728, 2910, 3062, 3244, 3339, 3616, 3711, 3730, 3825, 4007, 4159, 4197, 4322, 4341, 4379, 4417, 4531, 4599, 4694, 4713, 4789, 4808, 4971, 5085, 5104, 5161, 5180, 5199, 5294, 5305, 5476, 5677, 5696, 5772, 5791, 5848, 5886, 6049, 6068, 6144, 6163, 6258, 6402, 6440, 6497, 6516, 6630, 6641, 6660, 6679, 6736, 6774, 6850, 6869, 6899, 6918, 6994, 7013, 7032, 7051, 7070, 7089, 7108, 7127, 7146, 7222, 7271, 7290, 7309, 7366, 7385, 7404, 7442, 7461, 7480, 7491, 7499, 7594, 7624, 7643, 7662, 7681, 7719, 7738, 7814, 7863, 7882, 7901, 7939, 7958, 7977, 7996, 8034, 8053, 8072, 8091, 8121, 8159, 8186, 8216, 8235, 8254, 8273, 8311, 8330, 8341, 8349, 8368, 8444, 8463, 8474, 8493, 8531, 8569, 8588, 8626, 8664, 8683, 8694, 8702, 8713, 8721, 8751, 8789, 8808, 8816, 8827, 8846, 8884, 8903, 8922, 8941, 8971, 9036, 9066, 9085, 9104, 9123, 9142, 9161, 9180, 9199, 9218, 9256, 9294, 9313, 9324, 9343, 9362, 9381, 9419, 9438, 9476, 9514, 9533, 9544, 9552, 9563, 9571, 9582, 9601, 9639, 9658, 9666, 9677, 9696, 9734, 9753, 9772, 9791, 9802, 9821, 9886, 9897, 9916, 9935, 9954, 9973, 9992};
        /**
         * 闰腊月年份
         */
        private static final int[] LEAP_12 = {37, 56, 113, 132, 151, 189, 208, 227, 246, 284, 303, 341, 360, 379, 417, 436, 458, 477, 496, 515, 534, 572, 591, 629, 648, 667, 697, 716, 792, 811, 830, 868, 906, 925, 944, 963, 982, 1001, 1020, 1039, 1058, 1088, 1153, 1202, 1221, 1240, 1297, 1335, 1392, 1411, 1422, 1430, 1517, 1525, 1536, 1574, 3358, 3472, 3806, 3988, 4751, 4941, 5066, 5123, 5275, 5343, 5438, 5457, 5495, 5533, 5552, 5715, 5810, 5829, 5905, 5924, 6421, 6535, 6793, 6812, 6888, 6907, 7002, 7184, 7260, 7279, 7374, 7556, 7746, 7757, 7776, 7833, 7852, 7871, 7966, 8015, 8110, 8129, 8148, 8224, 8243, 8338, 8406, 8425, 8482, 8501, 8520, 8558, 8596, 8607, 8615, 8645, 8740, 8778, 8835, 8865, 8930, 8960, 8979, 8998, 9017, 9055, 9074, 9093, 9112, 9150, 9188, 9237, 9275, 9332, 9351, 9370, 9408, 9427, 9446, 9457, 9465, 9495, 9560, 9590, 9628, 9647, 9685, 9715, 9742, 9780, 9810, 9818, 9829, 9848, 9867, 9905, 9924, 9943, 9962, 10000};
        /**
         * 闰月
         */
        static final Map<Integer, Integer> LEAP = new HashMap<>(16);

        static {
            for (int y : LEAP_11) {
                LEAP.put(y, 13);
            }
            for (int y : LEAP_12) {
                LEAP.put(y, 14);
            }
        }

        /**
         * 缓存
         */
        private static final Map<Integer, TempYear> CACHE = new HashMap<>();

        /**
         * 年份
         */
        private final int year;

        /**
         * 月数组， 包括当前年内所有的月份数据
         */
        final TempMonth[] months = new TempMonth[15];

        /**
         * 各个节气的儒略日
         */
        final double[] julianDays = new double[31];

        /**
         * 各个节气的时间， 从前一年的 大雪开始， 包括当前年所有的节气， 一直到下一年的惊蛰(共1+24+6=31个)
         */
        private final LocalDateTime[] terms = new LocalDateTime[31];

        /**
         * 构造阴历年
         *
         * @param year 阴历年，农历年份
         */
        TempYear(int year) {
            // 以正月初一开始
            this.year = year;

            // 节气(中午12点)， 24节气为一年， 每个节气所在的 儒略日
            var jq = new double[27];
            // 合朔，即每月初一(中午12点) ， 每个合朔的儒略日, (从前一年冬月初一的儒略日开始， 向后计算16个月的儒略日)
            var hs = new double[16];
            // 每月天数
            var counts = new int[15];

            year -= 2000;
            // 从上年的大雪到下年的立春
            for (int i = 0; i < 31; i++) {
                // 精确的节气
                var t = 36525 * Util.saLonT((year + (17 + i) * 15d / 360) * Util.PI_2);
                t += Util.ONE_THIRD - Util.dtT(t);
                var julian = t + J2000;
                julianDays[i] = julian;
                terms[i] = Lunar.toDateTime(julian);
                // 按中午12点算的节气
                if (i > 0 && i < 28) {
                    jq[i - 1] = Math.round(t);
                }
            }

            // 冬至前的初一
            var w = Util.calcShuo(jq[0]);
            if (w > jq[0]) {
                w -= 29.5306;
            }

            // 递推每月初一
            for (int i = 0; i < 16; i++) {
                hs[i] = Util.calcShuo(w + 29.5306 * i);
            }

            // 每月天数
            for (int i = 0; i < 15; i++) {
                counts[i] = (int) (hs[i + 1] - hs[i]);
            }

            var currentYearLeap = LEAP.get(this.year);
            if (null == currentYearLeap) {
                currentYearLeap = -1;
                if (hs[13] <= jq[24]) {
                    int i = 1;
                    while (hs[i + 1] > jq[2 * i] && i < 13) {
                        i++;
                    }
                    currentYearLeap = i;
                }
            }
            // 计算月
            int prevYear = this.year - 1;
            var prevYearLeap = LEAP.get(prevYear);
            prevYearLeap = null == prevYearLeap ? -1 : prevYearLeap - 12;
            for (int i = 0, j = 1, y = prevYear, m = 11, cm; i < 15; i++, j++) {
                var isNextLeap = false;
                if (y == this.year || y == prevYear) {
                    cm = i == currentYearLeap || i == prevYearLeap ? -m : m;
                    isNextLeap = j == currentYearLeap || j == prevYearLeap;
                } else {
                    cm = m;
                }
                // 该年 所有 月 的数据
                months[i] = new TempMonth(y, cm, counts[i], hs[i] + J2000);
                if (!isNextLeap) {
                    m++;
                }
                if (m > 12) {
                    m = 1;
                    y++;
                }
            }
        }

        /**
         * 通过阴历年初始化
         *
         * @param year 阴历年
         * @return 阴历年
         */
        public static TempYear of(int year) {
            return CACHE.computeIfAbsent(year, TempYear::new);
        }

        /**
         * 获取当前年指定的月份
         * <table><td>月份</td>
         * <td>正<span style="font-size: 70.7%">0</span></td>
         * <td>二<span style="font-size: 70.7%">1</span></td>
         * <td>三<span style="font-size: 70.7%">2</span></td>
         * <td>四<span style="font-size: 70.7%">3</span></td>
         * <td>五<span style="font-size: 70.7%">4</span></td>
         * <td>六<span style="font-size: 70.7%">5</span></td>
         * <td>七<span style="font-size: 70.7%">6</span></td>
         * <td>八<span style="font-size: 70.7%">7</span></td>
         * <td>九<span style="font-size: 70.7%">8</span></td>
         * <td>十<span style="font-size: 70.7%">9</span></td>
         * <td>冬<span style="font-size: 70.7%">10</span></td>
         * <td>腊<span style="font-size: 70.7%">11</span></td>
         * </tr></table>
         *
         * @param month 月份下标, 注意月份下标， 比如三月的下标为2
         * @return 阴历月份
         */
        public TempMonth getMonth(int month) {
            for (var item : months) {
                if (item.year == year && month == item.month) {
                    return item;
                }
            }
            throw new IllegalArgumentException(year + "不存在 " + month + "的月份");
        }


        /**
         * 通过儒略日获取这一年的 阴历月份
         *
         * @param julian 阳历日期
         * @return 阴历月份
         */
        public TempMonth getMonth(double julian) {
            for (var month : months) {
                var end = month.julian;
                if (julian >= end) {
                    return month;
                }
            }
            throw new IllegalArgumentException("指定儒略日[" + julian + "]不在当前年度内[" + months[0].julian + ", " + months[14].julian + "]");
        }

        public void forEach(Consumer<TempMonth> consumer) {
            for (TempMonth month : months) {
                if (month.year == year) {
                    consumer.accept(month);
                }
            }
        }

        /**
         * 获取指定名称的节气时间(阳历时间), 默认从冬至日开始计算
         *
         * @param name 节气名称
         * @return 节气时间(阳历时间)
         */
        public LocalDateTime next(String name) {
            return next(terms[0], name);
        }

        /**
         * 获取从指定时间开始的下一个节气时间(阳历时间)， 例如：获取2022-2-24 15:53:12后的立春， 则返回时间 2023-02-04 10:42:21
         * 可以理解为返回 指定时间后的第一个与 指定名称匹配的节气
         *
         * @param dateTime 指定开始时间
         * @param name     节气名称
         * @return 节气时间(阳历时间)
         */
        public LocalDateTime next(LocalDateTime dateTime, String name) {
            for (int i = 0, len = this.terms.length, n = 23; i < len; i++, n++) {
                var cur = this.terms[i];
                if (cur.isBefore(dateTime)) {
                    continue;
                }
                var index = Util.rem(n, 24);
                var item = TERMS[index];
                if (Objects.equals(item, name)) {
                    return cur;
                }
            }
            throw new IllegalArgumentException();
        }

        /**
         * 获取 指定日期附近的节气日期(指阳历日期)
         *
         * @param dateTime 指定日期(指阳历日期)
         * @param next     指定日期(指阳历日期)后一个节气日期(指阳历日期)
         * @param from     true：获取附近的节令日期(指阳历日期)； false：获取附近的气令阳历日期(指阳历日期)
         * @return 指定日期附近的节气日期(指阳历日期)
         */
        public LocalDateTime near(LocalDateTime dateTime, boolean next, int from) {
            for (int i = from, len = this.terms.length; i < len; i += 2) {
                var cur = this.terms[i];
                if (cur.isBefore(dateTime)) {
                    continue;
                }
                if (next) {
                    return cur;
                }
                if (i > 1) {
                    return this.terms[i - 2];
                }
                var year = TempYear.of(this.year - 1);
                return year.terms[31 + i - 2];
            }
            throw new IllegalArgumentException();
        }

        public LocalDateTime nearJie(LocalDateTime dateTime, boolean forward) {
            return near(dateTime, forward, 0);
        }

        public LocalDateTime nearQi(LocalDateTime dateTime, boolean forward) {
            return near(dateTime, forward, 1);
        }

        @Override
        public String toString() {
            return year + "年";
        }
    }

    /**
     * 临时月
     *
     * @param year   农历年
     * @param month  农历月份(从0开始到11月, 闰月为负， 如 -2 表示 闰三月)
     * @param days   天数
     * @param julian 初一的儒略日
     */
    record TempMonth(int year, int month, int days, double julian) {

        /**
         * 是否闰月
         *
         * @return true/false
         */
        public boolean isLeap() {
            return month < 0;
        }

        @Override
        public String toString() {
            return Year.format(year) + "年 " + (isLeap() ? "闰" : "") + Month.ARRAY[Math.abs(month) - 1] + "月(" + days + "天)";
        }

        public LocalDate getLocalDate() {
            return toDateTime(julian).toLocalDate();
        }
    }
}
