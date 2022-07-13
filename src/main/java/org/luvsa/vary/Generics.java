package org.luvsa.vary;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * 获取泛型参数工具
 * Reflect
 * @author Aglet
 * @create 2022/7/1 13:53
 */
public final class Generics {

	private Generics() {
		throw new AssertionError("No org.luvsa.vary.Generics instances for you!");
	}

	/**
	 * 处理泛型参数
	 *
	 * @param clazz    泛型 Class 变量
	 * @param index    参数下标
	 * @param consumer 泛型参数处理器
	 */
	public static void accept(Class<?> clazz, int index, Consumer<Class<?>> consumer) {
		accept(clazz.getGenericSuperclass(), index, consumer);
	}

	/**
	 * 处理泛型参数, 存在问题：<pre>{@code
	 *  var list = new ArrayList<String>();
	 *  Generics.accept(list.getClass(), 0, aClass -> {
	 * 	    System.out.println(aClass);
	 *  }); // 存在泛型参数类型擦除问题 java.lang.RuntimeException: 泛型类型[E]错误！
	 * }</pre>
	 *
	 * @param type     泛型参数对象
	 * @param index    参数下标
	 * @param consumer 泛型参数处理器
	 */
	public static void accept(Type type, int index, Consumer<Class<?>> consumer) {
		submit(type, index, arg -> {
			if (arg instanceof Class<?> clazz) {
				consumer.accept(clazz);
				return;
			}
			throw new IllegalArgumentException("泛型类型[" + arg + "]错误！");
		});
	}

	/**
	 * 处理泛型参数
	 *
	 * @param type     泛型参数对象
	 * @param index    参数下标
	 * @param consumer 泛型参数处理器
	 */
	public static void submit(Type type, int index, Consumer<Type> consumer) {
		if (type instanceof ParameterizedType param) {
			// 获取实现泛型参数
			var arguments = param.getActualTypeArguments();
			// 获取指定下标的泛型参数
			var argument = arguments[index];
			consumer.accept(argument);
			return;
		}
		throw new IllegalArgumentException("未找到[" + type + "] 的泛型参数！");
	}
}
