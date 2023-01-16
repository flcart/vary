package org.luvsa.lunar;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 地支
 *
 * @author Aglet
 * @create 2023/1/6 14:51
 */
public class Zhi implements Zurich {
    /**
     * 地支数组
     * <p>
     * 为了满足地支与年限对应，所以调整了位置， 不以子为首
     *
     * <table>
     *     <tr>
     *         <td>子<span style="font-size: 70.7%">4</span></td>
     *         <td>丑<span style="font-size: 70.7%">5</span></td>
     *         <td>寅<span style="font-size: 70.7%">6</span></td>
     *         <td>卯<span style="font-size: 70.7%">7</span></td>
     *         <td>辰<span style="font-size: 70.7%">8</span></td>
     *         <td>巳<span style="font-size: 70.7%">9</span></td>
     *         <td>午<span style="font-size: 70.7%">10</span></td>
     *         <td>未<span style="font-size: 70.7%">11</span></td>
     *         <td>申<span style="font-size: 70.7%">0</span></td>
     *         <td>酉<span style="font-size: 70.7%">1</span></td>
     *         <td>戌<span style="font-size: 70.7%">2</span></td>
     *         <td>亥<span style="font-size: 70.7%">3</span></td>
     *     </tr>
     * </table>
     */
    public static char[] ARRAY = {'申', '酉', '戌', '亥', '子', '丑', '寅', '卯', '辰', '巳', '午', '未'};

    /**
     * 地支和时间 映射关系
     */
    public static LocalTime[] TIME_ARRAY = {
            /*子*/ LocalTime.of(1, 0),
            /*丑*/ LocalTime.of(3, 0),
            /*寅*/ LocalTime.of(5, 0),
            /*卯*/ LocalTime.of(7, 0),
            /*辰*/ LocalTime.of(9, 0),
            /*巳*/ LocalTime.of(11, 0),
            /*午*/ LocalTime.of(13, 0),
            /*未*/ LocalTime.of(15, 0),
            /*申*/ LocalTime.of(17, 0),
            /*酉*/ LocalTime.of(19, 0),
            /*戌*/ LocalTime.of(21, 0),
            /*亥*/ LocalTime.of(23, 0)};

    /**
     * 天干坐标
     */
    private final int index;

    private Zhi(int index) {
        this.index = index;
    }

    /**
     * 构建地支实例
     *
     * @param index 序号
     * @return 地支实列
     */
    public static Zhi of(int index) {
        return new Zhi(index);
    }

    /**
     * 获取地支元素的下标
     *
     * @param index 当前下标值(可以是任意值，无需担心越界问题)
     * @return 地支元素的下标(0 ~ 11)
     * @apiNote 当下标超出 {@link #ARRAY 地支数组}，就会进行取余运算， 最终返回 0~11 中其中一个值
     */
    public static int index(int index) {
        return index(index, 0);
    }

    /**
     * 获取地支元素的下标
     *
     * @param index  当前下标值(可以是任意值，无需担心越界问题)
     * @param offset 偏移量
     * @return 地支元素的下标(0 ~ 11)
     * @apiNote 当下标超出 {@link #ARRAY 地支数组}，就会进行取余运算， 最终返回 0~11 中其中一个值
     */
    public static int index(int index, int offset) {
        return Util.rem(index + offset, 12);
    }

    public static int at(LocalTime time) {
        for (int i = 0; i < TIME_ARRAY.length; i++) {
            var item = TIME_ARRAY[i];
            if (time.isBefore(item)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var zhi = (Zhi) o;
        return index == zhi.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return String.valueOf(value());
    }

    @Override
    public int index() {
        return index(this.index);
    }

    @Override
    public char value() {
        return ARRAY[index()];
    }

    Zhi next(int offset) {
        var index = index(this.index, offset);
        return new Zhi(index);
    }
}
