package org.luvsa.reflect;

import org.luvsa.lang.Strings;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Aglet
 * @create 2022/7/13 18:27
 */
public final class Reflects {

    private Reflects() {
        throw new AssertionError("No org.luvsa.vary.Reflects instances for you!");
    }

    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
    private static final String CGLIB_RENAMED_METHOD_PREFIX = "CGLIB$";

    private static final String GET_METHOD_PREFIX = "get";
    private static final String GET_BOOL_METHOD_PREFIX = "is";

    public static final Predicate<Method> USER_DECLARED_METHODS = method -> !method.isBridge() && !method.isSynthetic() && (method.getDeclaringClass() != Object.class);

    private final static BiPredicate<String, Method> FIND_GET_METHOD = (name, method) -> {
        if (method.getParameterCount() > 0) {
            return false;
        }
        var type = method.getReturnType();
        if (type == void.class || type == Void.class) {
            return false;
        }
        var methodName = method.getName();
        if (Objects.equals(methodName, name)) {
            return true;
        }
        var capitalize = Strings.capitalize(name);
        return Objects.equals(GET_METHOD_PREFIX + capitalize, methodName) ||
                Objects.equals(GET_BOOL_METHOD_PREFIX + capitalize, methodName);
    };


    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<>(256);
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);

    private static final Map<Field, Method> GET_FIELDS = new ConcurrentHashMap<>(256);


    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, EMPTY_CLASS_ARRAY);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        return findMethod(clazz, method -> name.equals(method.getName()) && hasSameParams(method, paramTypes));
    }

    private static boolean hasSameParams(Method method, Class<?>[] paramTypes) {
        return paramTypes.length == method.getParameterCount() &&
                Arrays.equals(paramTypes, method.getParameterTypes());
    }

    public static Method findMethod(Class<?> clazz, Predicate<Method> predicate) {
        var search = clazz;
        while (search != null) {
            var methods = search.isInterface() ?
                    search.getMethods() :
                    getDeclaredMethods(search, false);
            for (var method : methods) {
                if (predicate.test(method)) {
                    return method;
                }
            }
            search = search.getSuperclass();
        }
        return null;
    }


    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        return findField(clazz, field ->
                (name == null || Objects.equals(name, field.getName())) &&
                        (type == null || Objects.equals(type, field.getType()))
        );
    }

    public static Field findField(Class<?> clazz, Predicate<Field> predicate) {
        var search = clazz;
        while (search != null) {
            var fields = getDeclaredFields(search);
            for (var field : fields) {
                if (predicate.test(field)) {
                    return field;
                }
            }
            search = search.getSuperclass();
        }
        return null;
    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        var result = declaredFieldsCache.get(clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                if (result.length == 0) {
                    return EMPTY_FIELD_ARRAY;
                }
                declaredFieldsCache.put(clazz, result);
                return result;
            } catch (Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }

    public static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
    }

    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, EMPTY_OBJECT_ARRAY);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not access method[" + method + "]: " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("方法内部执行错误！", e);
        } catch (IllegalArgumentException e) {
            var joiner = new StringJoiner(", ");
            var parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                var item = parameters[i];
                joiner.add(item.getType().getName() + " " + item.getName() + "/* " + args[i] + " */");
            }
            var aClass = method.getDeclaringClass();
            throw new IllegalStateException("方法调用出错： " + aClass.getName() + "#" + method.getName() + "(" + joiner + ")", e);
        }
    }

    public static void doWithFields(Class<?> clazz, Consumer<Field> consumer) {
        doWithFields(clazz, consumer, item -> true);
    }

    public static void doWithFields(Class<?> clazz, Consumer<Field> consumer, Predicate<Field> predicate) {
        var current = clazz;
        do {
            var fields = getDeclaredFields(current);
            for (var field : fields) {
                if (predicate.test(field)) {
                    try {
                        consumer.accept(field);
                    } catch (Throwable ex) {
                        throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                    }
                }
            }
            current = current.getSuperclass();
        } while (current != null && current != Object.class);
    }


    public static void doWithMethods(Class<?> clazz, Consumer<Method> consumer) {
        doWithMethods(clazz, consumer, item -> true);
    }

    public static void doWithMethods(Class<?> clazz, Consumer<Method> consumer, Predicate<Method> predicate) {
        if (predicate == USER_DECLARED_METHODS && clazz == Object.class) {
            // nothing to introspect
            return;
        }

        var methods = getDeclaredMethods(clazz, false);
        for (var method : methods) {
            if (predicate.test(method)) {
                try {
                    consumer.accept(method);
                } catch (Throwable ex) {
                    throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + ex);
                }
            }
        }

        // Keep backing up the inheritance hierarchy.
        var superclass = clazz.getSuperclass();
        if (superclass != null && (predicate != USER_DECLARED_METHODS || superclass != Object.class)) {
            doWithMethods(superclass, consumer, predicate);
        } else if (clazz.isInterface()) {
            for (var item : clazz.getInterfaces()) {
                doWithMethods(item, consumer, predicate);
            }
        }
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return getDeclaredMethods(clazz, true);
    }

    private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
        var result = declaredMethodsCache.get(clazz);
        if (result == null) {
            try {
                var methods = clazz.getDeclaredMethods();
                var list = findConcreteMethodsOnInterfaces(clazz);
                if (list.isEmpty()) {
                    return methods;
                }
                result = new Method[methods.length + list.size()];
                var index = methods.length;
                System.arraycopy(methods, 0, result, 0, index);
                for (var method : list) {
                    result[++index] = method;
                }
            } catch (Throwable e) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", e);
            }
        }
        if (result.length == 0) {
            return result;
        }
        declaredMethodsCache.put(clazz, result);
        if (defensive) {
            return result.clone();
        }
        return result;
    }

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        var result = new ArrayList<Method>();
        for (var ifc : clazz.getInterfaces()) {
            for (var ifcMethod : ifc.getMethods()) {
                var modifiers = ifcMethod.getModifiers();
                if (Modifier.isAbstract(modifiers)) {
                    continue;
                }
                result.add(ifcMethod);
            }
        }
        return result;
    }


    public static Method[] getUniqueDeclaredMethods(Class<?> clazz, Predicate<Method> predicate) {
        final var methods = new ArrayList<Method>(20);
        doWithMethods(clazz, method -> {
            var knownSignature = false;
            Method methodBeingOverriddenWithCovariantReturnType = null;
            for (var existingMethod : methods) {
                if (method.getName().equals(existingMethod.getName()) &&
                        method.getParameterCount() == existingMethod.getParameterCount() &&
                        Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
                    // Is this a covariant return type situation?
                    if (existingMethod.getReturnType() != method.getReturnType() &&
                            existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                        methodBeingOverriddenWithCovariantReturnType = existingMethod;
                    } else {
                        knownSignature = true;
                    }
                    break;
                }
            }
            if (methodBeingOverriddenWithCovariantReturnType != null) {
                methods.remove(methodBeingOverriddenWithCovariantReturnType);
            }

            if (!knownSignature && !isCglibRenamedMethod(method)) {
                methods.add(method);
            }

        }, predicate);
        return methods.toArray(EMPTY_METHOD_ARRAY);
    }

    public static boolean isCglibRenamedMethod(Method renamedMethod) {
        var name = renamedMethod.getName();
        if (name.startsWith(CGLIB_RENAMED_METHOD_PREFIX)) {
            var i = name.length() - 1;
            while (i >= 0 && Character.isDigit(name.charAt(i))) {
                i--;
            }
            return (i > CGLIB_RENAMED_METHOD_PREFIX.length() && (i < name.length() - 1) && name.charAt(i) == '$');
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> type, int size) {
        return (T[]) Array.newInstance(type, size);
    }

    public static Object getField(String name, Object target) {
        var aClass = target.getClass();
        var method = findMethod(aClass, item -> FIND_GET_METHOD.test(name, item));
        if (method == null) {
            var field = findField(aClass, name);
            if (field == null) {
                return null;
            }
            return getField(field, target, true);
        }
        return invokeMethod(method, target);
    }

    public static Object getField(Field field, Object target) {
        return getField(field, target, false);
    }

    private static Object getField(Field field, Object target, boolean forlorn) {
        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            if (forlorn) {
                // 放弃挣扎
                throw new RuntimeException("无法获取字段 ：" + field + " 的值！", ex);
            }
            // 还可以拯救一下
            var cur = field.getName();
            var aClass = field.getDeclaringClass();
            var method = findMethod(aClass, item -> FIND_GET_METHOD.test(cur, item));
            if (method == null) {
                throw new RuntimeException("无法获取字段 ：" + field + " 的值！", ex);
            }
            return invokeMethod(method, target);
        }
    }
}
