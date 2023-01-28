package org.luvsa.html;

import jdk.jfr.Name;

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