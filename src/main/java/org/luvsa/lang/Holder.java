package org.luvsa.lang;

/**
 * 元素暂存器
 *
 * @author Aglet
 * @create 2022/9/21 10:31
 */
public interface Holder<T> {

    /**
     * 获取 Holder 中的元素
     *
     * @return Holder 中的元素对象
     */
    T get();

    /**
     * 设置holder 元素
     *
     * @param value 元素(指定类型)
     */
    void set(T value);

    /**
     * 设置Holder元素
     *
     * @param value 元素(任意类型)
     */
    void put(Object value);
}
