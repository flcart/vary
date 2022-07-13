package org.luvsa.vary;

import org.luvsa.vary.VaryTest.Prism;

/**
 * @author Aglet
 * @create 2022/7/5 17:31
 */
public class Talent {

    private int point;

    private String text;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Conversion
    public Prism toPrism(){
        return new Prism();
    }
}
