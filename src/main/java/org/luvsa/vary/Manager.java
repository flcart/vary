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
public abstract class Manager<T> implements Iterable<Class<?>> {

    /**
     * 数据转换函数的缓存
     */
    protected final Map<Class<?>, T> cache = new ConcurrentHashMap<>();

    protected final Map<Class<?>, List<T>> map = new ConcurrentHashMap<>();

    protected static final Loader loader = new DefaultLoader();

    public void put(Class<?> key, T value) {
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

    public T get(Class<?> key) {
        var value = cache.get(key);
        if (value == null) {
            var list = this.map.get(key);
            if (list == null) {
                return null;
            }
//            var moduleA = Reflections.getCallerClass(Vary.class).getModule();
            for (var item : list) {
//                var moduleB = item.getClass().getModule();
                return item;
            }
            throw new IllegalArgumentException();
        }
        return value;
    }

    @Override
    public Iterator<Class<?>> iterator() {
        var list = new ArrayList<Class<?>>();
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
    protected abstract T offer(Class<?> clazz) throws Exception;

    protected boolean isEmpty() {
        return cache.isEmpty() && map.isEmpty();
    }

    protected T computeIfAbsent(DataType type, Function<Class<?>, T> function) {
        var clazz = type.getClazz();
        var value = get(clazz);
        if (value == null) {
            var apply = function.apply(clazz);
            if (apply == null) {
                return null;
            }
            if (containsKey(clazz)) {
                return apply;
            }
            put(clazz, apply);
            return apply;
        }
        return null;
    }

    public boolean containsKey(Class<?> clazz) {
        return cache.containsKey(clazz) || map.containsKey(clazz);
    }
}
