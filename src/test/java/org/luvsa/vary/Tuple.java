package org.luvsa.vary;


import java.util.List;

/**
 * @author Aglet
 * @create 2022/7/5 17:30
 */
public class Tuple implements Future {

    private int point;

    private List<Item> means;

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public List<Item> getMeans() {
        return means;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setMeans(List<Item> means) {
        this.means = means;
    }
}
