package org.luvsa.lunar;

/**
 * @author Aglet
 * @create 2023/1/6 14:59
 */
public interface Zurich {

    /**
     * @return {@link Gan 天干}下标或{@link Zhi 地支}下标
     */
    int index();

    /**
     * @return {@link Gan 天干}元素或{@link Zhi 地支}元素
     */
    char value();


}
