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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 数据转换函数的管理器
 *
 * @author 49897
 */
public abstract class Manager<T> implements Iterable<Type> {
    /**
     * 数据转换函数的缓存
     */
    protected final Map<Type, T> cache = new ConcurrentHashMap<>();

    /**
     * 数据转换缓存
     */
    protected final Map<Type, List<T>> map = new ConcurrentHashMap<>();

    protected static final Loader loader = new DefaultLoader();

    protected void put(Type key, T value) {
        var list = map.get(key);
        if (list == null) {
            var remove = cache.remove(key);
            if (remove == null) {
                // cache 中没有值， 直接存放
                cache.put(key, value);
            } else {
                // 存在值，则形成 列表
                list = new ArrayList<>();
                list.add(remove);
                list.add(value);
                map.put(key, list);
            }
        } else {
            list.add(value);
        }
    }

    /**
     * 根据 类型获取 管理器中的 值
     *
     * @param key 数据类型
     * @return 值
     */
    protected <R> R get(Type key, Function<T, R> function) {
        if (key instanceof ParameterizedType param) {
            key = param.getRawType();
        }
        var value = cache.get(key);
        if (value == null) {
            var list = this.map.get(key);
            if (list == null) {
                throw new NoSuchElementException(this + " not has " + key.getTypeName());
            }
            for (T item : list) {
                try {
                    return function.apply(item);
                } catch (Exception ignore) {
                }
            }
            return null;
        }
        return function.apply(value);
    }

    @Override
    public Iterator<Type> iterator() {
        var list = new ArrayList<Type>();
        list.addAll(cache.keySet());
        list.addAll(map.keySet());
        return list.iterator();
    }

    protected boolean isEmpty() {
        return cache.isEmpty() && map.isEmpty();
    }
}
