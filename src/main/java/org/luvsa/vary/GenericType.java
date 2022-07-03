package org.luvsa.vary;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 泛型数据
 *
 * @author Aglet
 * @create 2022/7/1 16:30
 */
public class GenericType implements DataType {

    /**
     * 数据类型
     */
    private final Class<?> value;

    /**
     * 泛型参数
     */
	private final Type generic;

	public GenericType(Class<?> value, Type generic) {
		this.value = value;
		this.generic = generic;
	}

	@Override
	public Class<?> getClazz() {
		return value;
	}

	@Override
	public Type getGenericType() {
		return generic;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var that = (GenericType) o;
		return Objects.equals(value, that.value) && Objects.equals(generic, that.generic);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, generic);
	}
}
