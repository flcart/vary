package org.luvsa.lang;

import org.junit.jupiter.api.Test;

import java.util.Objects;

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
//        ContextHolder.set(15);
//        var aLong = ContextHolder.get(int.class);
//        var integer = ContextHolder.get(Integer.class);
//        System.out.println(aLong);
//        System.out.println(integer);
        for (int i = 0; i < 10; i++) {
            try {
                error();
                System.out.println("11");
            } catch (Exception e) {
                ContextHolder.set(e);
            }
        }
        throw Objects.requireNonNull(ContextHolder.get(RuntimeException.class));
    }


    void error() {
        throw new RuntimeException("这是一个错误！");
    }
}