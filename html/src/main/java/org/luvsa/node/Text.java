package org.luvsa.node;

/**
 * 文本节点
 *
 * @author Aglet
 * @create 2023/2/18 13:45
 */
public class Text extends Leaf {

    public Text(String s) {
        this.value = s;
    }

    public static Text of(String s) {
        return new Text(s);
    }

    @Override
    public String getName() {
        return "#text";
    }

}
