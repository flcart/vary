package org.luvsa.vary;

/**
 * 数据转换类型支持
 * <p>
 * 自动转换
 *
 * @author Aglet
 * @create 2022/6/29 16:44
 */
public interface Support {

    /**
     * 可供数据转换的数据类型
     *
     * @return 数据类型
     */
    default Class<?>[] getTypes() {
        return Util.EMPTY;
    }

}
