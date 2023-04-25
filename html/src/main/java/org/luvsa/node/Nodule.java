package org.luvsa.node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aglet
 * @create 2023/3/27 14:42
 */
public interface Nodule {

    List<Nodule> list = new ArrayList<>();

    Node apply(String s);

    static Node of(String s) {
        for (var nodule : list) {
            try {
                var apply = nodule.apply(s);
                if (apply != null) {
                    return apply;
                }
            } catch (Exception ignore) {
            }
        }
        throw new IllegalStateException(s);
    }
}
