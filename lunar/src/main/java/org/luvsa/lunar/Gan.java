package org.luvsa.lunar;

import lombok.Data;

import java.util.Objects;

/**
 * 天干
 *
 * @author Aglet
 * @create 2023/1/6 14:46
 */
@Data
public class Gan implements Zurich {

    /**
     * 天干数组
     * <p>
     * 为了满足天干与年限对应，所以调整了位置， 不以甲为首
     * <table>
     *     <tr>
     *         <td>甲<span style="font-size: 70.7%">4</span></td>
     *         <td>乙<span style="font-size: 70.7%">5</span></td>
     *         <td>丙<span style="font-size: 70.7%">6</span></td>
     *         <td>丁<span style="font-size: 70.7%">7</span></td>
     *         <td>戊<span style="font-size: 70.7%">8</span></td>
     *         <td>己<span style="font-size: 70.7%">9</span></td>
     *         <td>庚<span style="font-size: 70.7%">0</span></td>
     *         <td>辛<span style="font-size: 70.7%">1</span></td>
     *         <td>壬<span style="font-size: 70.7%">2</span></td>
     *         <td>癸<span style="font-size: 70.7%">3</span></td>
     *     </tr>
     * </table>
     */
    public static final char[] ARRAY = {'庚', '辛', '壬', '癸', '甲', '乙', '丙', '丁', '戊', '己'};

    /**
     * 天干坐标
     */
    private final int index;

    /**
     * 构建天干
     *
     * @param index 天干下标
     */
    private Gan(int index) {
        this.index = index;
    }

    /**
     * 构建天干实列
     *
     * @param index 天干下标
     * @return 天干
     */
    public static Gan of(int index) {
        return new Gan(index);
    }


    /**
     * 获取天干元素的下标
     *
     * @param index 下标(可以是任意值，无需担心越界问题)
     * @return 天干元素的下标(0 ~ 9)
     * @apiNote 当下标超出 {@link #ARRAY 天干数组}，就会进行取余运算， 最终返回 0~9 中其中一个值
     */
    static int index(int index) {
        return index(index, 0);
    }

    Gan next(int offset) {
        var index = index(this.index, offset);
        return new Gan(index);
    }

    /**
     * 获取天干元素的下标
     *
     * @param index  当前下标值(可以是任意值，无需担心越界问题)
     * @param offset 偏移量
     * @return 天干元素的下标(0 ~ 9)
     * @apiNote 当下标超出 {@link #ARRAY 天干数组}，就会进行取余运算， 最终返回 0~9 中其中一个值
     */
    public static int index(int index, int offset) {
        return Util.rem(index + offset, 10);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var gan = (Gan) o;
        return index == gan.index;
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
}
