package org.luvsa.vary;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Aglet
 * @create 2022/8/4 13:50
 */
class Caner {

    @Test
    void start(){
        var property = System.getProperty("java.class.path");
        var split = property.split(";");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
