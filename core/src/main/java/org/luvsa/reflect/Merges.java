package org.luvsa.reflect;

import java.io.Serial;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Aglet
 * @create 2022/11/8 14:16
 */
public final class Merges {

    private final static Predicate<Field> filters = field -> {
        int modifiers = field.getModifiers();
        return (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) ||
                field.isAnnotationPresent(Serial.class);
    };


    private Merges() {
        throw new AssertionError("No com.jdy.angel.utils.Merges instances for you!");
    }

    /**
     * 检查 两者是否存在改动
     *
     * @param source 源数据
     * @param change 疑数据(副本)
     * @return true： 表示两者存在改动，
     */
    public static boolean check(Object source, Object change) {
        var sources = new HashMap<String, Entry<Field, Object>>();
        Reflects.doWithFields(source.getClass(), field -> {
            if (filters.test(field)) {
                return;
            }
            var value = Reflects.getValue(field, source);
            sources.put(field.getName(), new KeyValue(field, value));
        });
        var set = new HashSet<Field>();
        Reflects.doWithFields(change.getClass(), field -> {
            if (filters.test(field)) {
                return;
            }
            var name = field.getName();
            var entry = sources.get(name);
            if (entry != null) {
                var target = entry.getValue();
                var value = Reflects.getValue(field, change);
                if (notEquals(value, target)) {
                    var key = entry.getKey();
                    Reflects.setValue(key, source, value);
                    set.add(key);
                }
            }
        });
        return !set.isEmpty();
    }

    /**
     * 比较 两个数据是否 ‘不相同’
     *
     * @param value  值
     * @param target 参考值
     * @return true ： 标识 值 和 参考值 不同
     */
    private static boolean notEquals(Object value, Object target) {
        // 1.如果 值 为空， 会被认为没有改动， 返回 false
        // 2.如果 值 和 参考值 相同， 则没有改动， 返回 false
        if (value == null || value.equals(target)) {
            return false;
        }
        // 3. 值为原始数据类型的默认值， 会被认为没有改动， 返回 false
        if (value instanceof Boolean b) {
            return b;
        }
        if (value instanceof Byte i) {
            return i != 0;
        }
        if (value instanceof Short i) {
            return i != 0;
        }
        if (value instanceof Integer i) {
            return i != 0;
        }
        if (value instanceof Long i) {
            return i != 0;
        }
        if (value instanceof Float f) {
            return f != 0;
        }
        if (value instanceof Double f) {
            return f != 0;
        }
        return true;
    }


    private static class KeyValue implements Entry<Field, Object> {
        private final Field key;

        private final Object value;

        public KeyValue(Field field, Object value) {
            this.key = field;
            this.value = value;
        }

        @Override
        public Field getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var keyValue = (KeyValue) o;
            return Objects.equals(key, keyValue.key) && Objects.equals(value, keyValue.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return key + " = " + value;
        }
    }
}
