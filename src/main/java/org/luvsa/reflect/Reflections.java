package org.luvsa.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/7/14 18:34
 */
public final class Reflections {

	private final static Map<Class<?>, Class<?>> PRIMITIVES = Map.of(
			byte.class, Byte.class,
			short.class, Short.class,
			int.class, Integer.class,
			long.class, Long.class,
			float.class, Float.class,
			double.class, Double.class,
			char.class, Character.class,
			boolean.class, Boolean.class
	);

	private Reflections() {
	}


	public static Class<?> wrap(Class<?> clazz) {
		return PRIMITIVES.getOrDefault(clazz, clazz);
	}

	/**
	 * 获取 调用目标 类方法的上层 类的 class 对象，
	 * 实现 {@link jdk.internal.reflect.Reflection#getCallerClass() 获取调用者的Class} 功能
	 *
	 * @param clazz 被调用的 class 对象
	 * @return 调用者的class对象
	 */
	public static Class<?> getCallerClass(Class<?> clazz) {
		var found = false;
		var name = clazz.getName();
		for (var item : Thread.currentThread().getStackTrace()) {
			var className = item.getClassName();
			if (Objects.equals(className, name)) {
				found = true;
			} else if (found) {
				try {
					return Class.forName(className);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new IllegalArgumentException("无法找到调用 " + clazz + " 的类对象！");
	}


	public static <T> T newInstance(Class<T> clazz) {
		try {
			var constructor = clazz.getConstructor();
			return constructor.newInstance();
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
		         IllegalAccessException e) {
			return null;
		}
	}
}
