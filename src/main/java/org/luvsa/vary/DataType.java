package org.luvsa.vary;

import java.lang.reflect.Type;

/**
 * 目标数据信息接口
 *
 * @author Aglet
 * @create 2022/7/1 14:53
 */
public interface DataType {

	/**
	 * 获取目标数据类型
	 *
	 * @return 数据类型
	 */
	Class<?> getClazz();

	/**
	 * 获取目标泛型参数类型
	 *
	 * @return 型参数类型(如果不是泛型 ， 则返回 null)
	 */
	Type getGenericType();

	/**
	 * 检测 {@link #getClazz() 目标数据类型}  与 {@link Class clz} 是否存在 继承或实现关系(如果{@link #getClazz() 目标数据类型}
	 * 和 {@link Class clz} 相同，返回 true)。基于 {@link Class#isAssignableFrom(Class)} 实现
	 *
	 * @param clz 目标数据类型
	 * @return {@link Class#isAssignableFrom(Class)}
	 */
	default boolean isAssignableFrom(Class<?> clz) {
		return getClazz().isAssignableFrom(clz);
	}

}
