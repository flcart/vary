package org.luvsa.vary;

import org.junit.jupiter.api.Test;
import org.luvsa.reflect.Generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dale
 * @create 2022/7/3 12:49
 */
class GenericsTest {

	@Test
	void accept() {
		var list = new ArrayList<Map<String, List<String>>>(){};
		Generics.accept(list.getClass(), 0, aClass -> {
			System.out.println(aClass);
		});
	}
}