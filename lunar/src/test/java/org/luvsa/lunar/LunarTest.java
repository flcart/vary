package org.luvsa.lunar;

import org.junit.jupiter.api.Test;
import org.luvsa.vary.Vary;

import java.time.LocalDateTime;

/**
 * @author Aglet
 * @create 2023/1/6 17:07
 */
class LunarTest {

    @Test
    void toDateTime() {
    }


    @Test
    void from() {
//        var from = Lunar.now();
//        System.out.println(from);
        var now = LocalDateTime.now();
        var change = Vary.change(now, Lunar.class);
        System.out.println(change);
    }
}