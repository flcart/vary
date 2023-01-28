package org.luvsa.html.res;

import jdk.jfr.Name;
import org.luvsa.html.Label;

/**
 * Hira-eth Rapacity
 * @author Aglet
 * @create 2023/1/11 11:00
 */
@Name("block.label")
public class Block implements Resource {

    @Override
    public void accept(String s) {
        Label.get(s);
    }

}