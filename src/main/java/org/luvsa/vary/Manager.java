/*
 * Copyright 2021 Dale's personal project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.luvsa.vary;

import org.luvsa.vary.TypeSupplier.Types;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 数据转换函数的管理器
 *
 * @author 49897
 */
public abstract class Manager<T> {

	/**
	 * 数据转换函数的缓存
	 */
	protected final Map<Class<?>, T> cache = new ConcurrentHashMap<>();

	/**
	 * 加载 TypeSupplier 的子接口
	 *
	 * @param clazz    TypeSupplier的子接口
	 * @param consumer 保存 TypeSupplier 实现对象的存储器
	 * @param <R>      TypeSupplier 的子接口
	 */
	protected <R extends TypeSupplier> void load(Class<R> clazz, BiConsumer<Class<?>, R> consumer) {
		// java spi 加载 Provider 实现
		var providers = ServiceLoader.load(clazz);
		for (var item : providers) {
			var aClass = item.getClass();
			// 获取 进行数据转换的数据类型
			var types = aClass.getAnnotation(Types.class);
			// 如果 注解 Types 没有声明， 则直接调 Provider.getTypes() 方法，
			var classes = types == null ? item.getTypes() : types.value();
			for (var clas : classes) {
				// 处理当前 被转换数据的类型 和 Provider
				// 一个 被转换数据的类型 对应一个 Provider
				consumer.accept(clas, item);
			}
		}
	}

	/**
	 * 获取转换函数
	 *
	 * @param clazz 目标数据类型
	 * @return 转换函数
	 */
	protected abstract T offer(Class<?> clazz);
}
