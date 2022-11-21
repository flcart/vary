package org.luvsa.vary;

import java.lang.reflect.Type;

/**
 * 当前数据转换工具的匹配性
 *
 * @author Aglet
 * @create 2022/7/13 11:41
 */
public interface Suitable {

	/**
	 * @param type 数据类型
	 * @return true： 表示当前转换器适合数据转换
	 * @apiNote 用于测试当前转换器是否适合进行数据转换
	 */
	boolean test(Type type);

}
