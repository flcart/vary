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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据转换函数的管理器
 *
 * @author 49897
 */
public abstract class Manager<T> {

    /**
     * 数据转换函数的缓存
     */
    protected final Map<Class<?>, T> cache = new ConcurrentHashMap<>() {
        @Override
        public T put(Class<?> key, T value) {
            if (containsKey(key)) {
                return value;
            }
            return super.put(key, value);
        }
    };

    protected static final Loader loader = new DefaultLoader();

    protected <R extends TypeSupplier> void handle(R item) {
    }

    /**
     * 获取转换函数
     *
     * @param clazz 目标数据类型
     * @return 转换函数
     */
    protected abstract T offer(Class<?> clazz) throws Exception;
}
