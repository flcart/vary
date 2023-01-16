package org.luvsa.lunar;

import java.time.LocalTime;

/**
 * @author Aglet
 * @create 2023/1/6 13:58
 */
public class Time extends Prism {

    /**
     * 秒
     */
    private final int value;

    Time(int seconds, int gan, int zhi) {
        super(gan, zhi);
        this.value = seconds;
    }

    public static Time of(Gan gan, LocalTime time) {
        time = adorn(time);
        var at = Zhi.at(time);
        var z = Zhi.index(at, 4);
        var offset = gan.index();
        offset += offset < 5 ? 3 : -2;
        offset <<= 1;
        var g = Gan.index(at, offset);
        return new Time(seconds(time), g, z);
    }

    /**
     * 添加一个秒处理方法，目前采用四舍五入方式
     *
     * @param time 时间
     * @return 处理过的时间
     */
    static LocalTime adorn(LocalTime time) {
        var second = time.getSecond();
        if (second == 0) {
            return time;
        }
        var floor = Math.floor(second);
        var s = floor / Lunar.SECONDS_PER_MINUTE;
        var round = Math.round(s);
        if (round > 0) {
            return time.plusSeconds(Lunar.SECONDS_PER_MINUTE - second);
        }
        return time;
    }

    /**
     * 将时间转换成秒
     *
     * @param time 时间
     * @return 秒
     */
    private static int seconds(LocalTime time) {
        var second = time.getSecond();
        var minute = time.getMinute() * Lunar.SECONDS_PER_MINUTE;
        var hour = time.getHour() * Lunar.SECONDS_PER_HOUR;
        return second + minute + hour;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
