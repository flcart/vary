package org.luvsa.vary.other;

import org.luvsa.vary.Strings;

/**
 * @author Aglet
 * @create 2022/7/5 17:16
 */
public final class Util {

    private Util() {
    }

    static String refer(String name) {
        if (name.startsWith("get")) {
            name = name.substring(3);
        }
        return Strings.uncapitalize(name);
    }
}
