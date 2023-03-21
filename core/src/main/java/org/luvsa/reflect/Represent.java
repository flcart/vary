package org.luvsa.reflect;

import org.luvsa.vary.Support;

/**
 * @author Aglet
 * @create 2023/3/18 11:16
 */
public interface Represent extends Support {

    /**
     * 数据转换
     *
     * @param o 源
     * @return 值
     */
    Object next(Object o);
}
