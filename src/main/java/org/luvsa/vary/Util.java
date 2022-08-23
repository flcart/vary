package org.luvsa.vary;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Aglet
 * @create 2022/7/15 11:27
 */
class Util {

    final static List<Vary> list = new ArrayList<>();

    static {
        var load = ServiceLoader.load(Vary.class);
        for (var item : load) {

            list.add(item);
        }
    }
}
