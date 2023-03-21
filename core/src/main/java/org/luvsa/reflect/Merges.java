package org.luvsa.reflect;

import org.luvsa.lang.Arrays;

import java.io.Serial;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 合并工具
 *
 * @author Aglet
 * @create 2022/11/8 14:16
 */
@SuppressWarnings("all")
public final class Merges {

    private final static Predicate<Field> filters = field -> {
        int modifiers = field.getModifiers();
        return (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) ||
                field.isAnnotationPresent(Serial.class);
    };

    private final static Class<? extends Annotation>[] annotations;

    static {
        String[] array = {"org.luvsa.annotation.Nullable", "org.jetbrains.annotations.Nullable", "org.springframework.lang.Nullable"};
        var list = new ArrayList<Class<? extends Annotation>>();
        for (var s : array) {
            try {
                var clazz = (Class<? extends Annotation>) Class.forName(s);
                list.add(clazz);
            } catch (ClassNotFoundException e) {
                //
            }
        }
        annotations = list.toArray(new Class[0]);
    }


    private Merges() {
        throw new AssertionError("No " + Merges.class + " instances for you!");
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
        // 收集源数据所有字段
        Reflects.doWithFields(source.getClass(), field -> {
            if (filters.test(field)) {
                return;
            }
            var value = Reflects.getValue(field, source);
            sources.put(field.getName(), new KeyValue(field, value));
        });
        var set = new HashSet<Field>();
        // 数据校验
        Reflects.doWithFields(change.getClass(), field -> {
            if (filters.test(field)) {
                return;
            }
            var name = field.getName();
            var entry = sources.get(name);
            if (entry == null) {
                // 源数据不存在这个字段， 所以最好使用相同类对象
                return;
            }
            // 源数据字段值
            var target = entry.getValue();
            // 目标数据字段值
            var value = Reflects.getValue(field, change);

            if (equals(value, target, () -> check0(field))) {
                // 如果'源数据字段值'与'目标数据字段值'相同，则认为未发生字段改动，直接跳过
                return;
            }
            // 收集改动的字段
            var key = entry.getKey();
            Reflects.setValue(key, source, value);
            set.add(key);
        });
        return !set.isEmpty();
    }

    private static boolean check0(Field field) {
        if (Arrays.has(item -> field.isAnnotationPresent(item), annotations)) {
            return false;
        }
        var type = field.getType();
        if (Reflections.PRIMITIVES.values().contains(type)) {
            return false;
        }
        return true;
    }

    /**
     * 比较 两个数据是否 ‘相同’
     *
     * @param value  标识值
     * @param target 参考值
     * @return true ： ‘标识值’和‘参考值’相同
     */
    private static boolean equals(Object value, Object target, Supplier<Boolean> supplier) {
        if (value == null) {
            //1. '标识值' 为空， '目标值' 也为空， 认为没有改动
            //2. '标识值' 为空， '目标值' 为 '初始值', 认为没有改动
            return target == null || supplier.get();
        }
        if (Objects.equals(value, target)) {
            // '标识值' 与 '目标值' 相同， 认为没有改动， 直接返回
            return true;
        }
        // 3. 值为原始数据类型的默认值， 会被认为没有改动， 返回 false
        // 默认值 看作 null
        if (value instanceof Boolean b) {
            // false 为 默认值
            return !b && supplier.get();
        }
        if (value instanceof Byte i) {
            return i == 0 && supplier.get();
        }
        if (value instanceof Short i) {
            return i == 0 && supplier.get();
        }
        if (value instanceof Integer i) {
            return i == 0 && supplier.get();
        }
        if (value instanceof Long i) {
            return i == 0 && supplier.get();
        }
        if (value instanceof Float f) {
            return f == 0 && supplier.get();
        }
        if (value instanceof Double f) {
            return f == 0 && supplier.get();
        }
        return false;
    }

    /**
     * 键-值
     */
    private static class KeyValue implements Entry<Field, Object> {
        /**
         * 类的成员属性
         */
        private final Field key;

        /**
         * 属性值
         */
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
