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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    public void put(Type key, T value) {
        var list = map.get(key);
        if (list == null) {
            var remove = cache.remove(key);
            if (remove == null) {
                cache.put(key, value);
            } else {
                list = new ArrayList<>();
                list.add(remove);
                list.add(value);
                map.put(key, list);
            }
        } else {
            list.add(value);
        }
    }

    public T get(Type key) {
        if (key instanceof ParameterizedType param) {
            key = param.getRawType();
        }
        var value = cache.get(key);
        if (value == null) {
            var list = this.map.get(key);
            if (list == null) {
                return null;
            }
            //TODO 这块存在一些问题
            for (var item : list) {
                return item;
            }
            return null;
        }
        return value;
    }

    @Override
    public Iterator<Type> iterator() {
        var list = new ArrayList<Type>();
        list.addAll(cache.keySet());
        list.addAll(map.keySet());
        return list.iterator();
    }

    /**
     * 获取转换函数
     *
     * @param clazz 目标数据类型
     * @return 转换函数
     */
    protected abstract T offer(Type clazz) throws Exception;

    protected boolean isEmpty() {
        return cache.isEmpty() && map.isEmpty();
    }

    protected T computeIfAbsent(Type type, Function<Type, T> function) {
        var value = get(type);
        if (value == null) {
            var apply = function.apply(type);
            if (apply == null) {
                return null;
            }
            if (containsKey(type)) {
                return apply;
            }
            put(type, apply);
            return apply;
        }
        return null;
    }

    public boolean containsKey(Type type) {
        return cache.containsKey(type) || map.containsKey(type);
    }
}
