package org.luvsa.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aglet
 * @create 2022/9/21 14:09
 */
class ContextHolderTest {

    @Test
    void get() {
    }

    @Test
    void set() {
        ContextHolder.set(15);
        var aLong = ContextHolder.get(int.class);
        var integer = ContextHolder.get(Integer.class);
        System.out.println(aLong);
        System.out.println(integer);
    }
}