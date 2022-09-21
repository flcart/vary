package org.luvsa.lang;

import org.junit.jupiter.api.Test;

/**
 * @author Aglet
 * @create 2022/9/21 9:58
 */
class ArraysTest {

    @Test
    void has() {
        var a = "12333dds";
        var has = Arrays.has(item -> item == 'a', a.toCharArray());
        System.out.println(has);
    }
}