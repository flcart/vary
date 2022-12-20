package org.luvsa.mate;

import jdk.jfr.Name;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/12/19 17:28
 */
@Name(Strategies.MOBILE)
public class MobileStrategy implements Strategy {
    @Override
    public Function<String, String> get() {
        return s -> {
            var length = s.length();
            var builder = new StringBuilder();
            var chars = s.toCharArray();
            for (int i = length - 1, j = 0, k = 0; i >= 0; i--, j++) {
                if (j == 4) {
                    j = 0;
                    k++;
                }
                var item = k == 1 ? '*' : chars[i];
                builder.append(item);
            }
            return builder.reverse().toString();
        };
    }
}
