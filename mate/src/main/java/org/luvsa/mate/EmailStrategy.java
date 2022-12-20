package org.luvsa.mate;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/12/20 10:04
 */
public class EmailStrategy implements Strategy {
    @Override
    public Function<String, String> get() {
        return s -> {
            var i = s.indexOf("@");
            if (i < 0) {
                return s;
            }
            var builder = new StringBuilder();
            var chars = s.toCharArray();
            for (int j = 0, k = 0, size = chars.length; j < size; j++) {
                if (j == 0 || j >= i) {
                    builder.append(chars[j]);
                } else if (k < 6){
                    builder.append('*');
                    k++;
                }
            }
            return builder.toString();
        };
    }


}
