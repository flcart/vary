package org.luvsa.vary;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;


/**
 * @author Aglet
 * @create 2022/6/29 17:34
 */
class VaryTest {

    @ParameterizedTest
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
    void strToDate(String txt) {
        var date = Vary.change(txt, Date.class);
        System.out.println(date);
    }

}