package org.luvsa.vary.other;

import org.luvsa.vary.Item;
import org.luvsa.vary.Talent;
import org.luvsa.vary.Tuple;
import org.luvsa.annotation.Types;
import org.luvsa.vary.Vary;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/5 17:29
 */
@Types(Tuple.class)
public class ToTuple implements Provider {

    @Override
    public Function<Object, ?> get(Type type) {
        return o -> {
            if (o instanceof Talent talent) {
                var tuple = new Tuple();
                tuple.setPoint(talent.getPoint());
                var text = talent.getText();
                var split = text.split("\n");
                var list = new ArrayList<Item>();
                for (String s : split) {
                    var change = Vary.change(s, Item.class);
                    list.add(change);
                }
                tuple.setMeans(list);
                return tuple;
            }
            return null;
        };
    }

}
