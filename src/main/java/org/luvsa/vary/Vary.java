package org.luvsa.vary;

import java.util.Objects;

/**
 * 数据转化接口
 *
 * @author Aglet
 * @create 2022/6/29 16:47
 */
public interface Vary {

	/**
	 * 转换数据
	 *
	 * @param value 当前数据
	 * @param cls   目标数据类型
	 * @param <T>   当前数据类型
	 * @param <R>   目标数据类型
	 * @return 目标数据
	 */
	@SuppressWarnings("unchecked")
	static <T, R> R change(T value, Class<R> cls) {
		return (R) change(value, new Clazz(cls));
	}

	/**
	 * 转换数据
	 *
	 * @param value 当前数据
	 * @param type  目标数据类型（包含目标数据类型信息）
	 * @param <T>   当前数据类型
	 * @return 目标数据
	 */
	static <T> Object change(T value, DataType type) {
		return DefaultVary.INSTANCE.apply(value, type);
	}

	/**
	 * 转换数据
	 *
	 * @param value 当前数据
	 * @param dev   转换失败时默认返回数据
	 * @param <T>   当前数据类型
	 * @param <R>   目标数据类型
	 * @return 目标数据
	 */
	static <T, R> R change(T value, R dev) {
		@SuppressWarnings("unchecked")
		var clazz = (Class<R>) dev.getClass();
		var con = change(value, clazz);
		return Objects.requireNonNullElse(con, dev);
	}

	/**
	 * 返回一个子数据，当转换失败时，返回当前数据，否则返回转换成功的数据
	 *
	 * @param value 当前数据
	 * @param clazz 目标数据类型
	 * @param <T>   当前数据类型
	 * @param <R>   目标数据类型
	 * @return 目标数据
	 */
	static <T, R> Object realize(T value, Class<R> clazz) {
		try {
			var rtn = change(value, clazz);
			return rtn == null ? value : rtn;
		} catch (Exception e) {
			return value;
		}
	}

	/**
	 * 转换数据
	 *
	 * @param value 当前数据
	 * @param type  目标数据类型(包含目标数据类型信息)
	 * @param <T>   当前数据类型
	 * @return 目标数据
	 */
	<T> Object apply(T value, DataType type);
}
