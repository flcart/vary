package org.luvsa.vary;

import org.luvsa.vary.TypeSupplier.Types;
import org.luvsa.vary.VaryTest.Item;
import org.luvsa.vary.string.SProvider;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/1 20:39
 */
@Types(Item.class)
public class ToItem implements SProvider {

    @Override
    public Function<String, ?> get(Class<?> clazz) {
        return s -> {
            var from = s.indexOf("、");
            if (from < 0) {
                throw new RuntimeException(s);
            }

            var sub = s.substring(0, from).strip();
            var guid = Vary.change(sub, int.class);
            var next = s.indexOf("：", from);
            if (next < from) {
                throw new RuntimeException(s);
            }
            var name = s.substring(from + 1, next).strip();
            var text = s.substring(next + 1).strip();

            return new Item() {

                @Override
                public int getGuid() {
                    return guid;
                }

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public String getText() {
                    return text;
                }
            };
        };
    }
}
