package org.luvsa.lunar;

import java.util.Objects;

/**
 * @author Aglet
 * @create 2023/1/6 14:45
 */
public class Pillar {
    public final static String GAN = "天干";
    public final static String ZHI = "地支";

    /**
     * 天干
     */
    Gan gan;

    /**
     * 地支
     */
    Zhi zhi;

    public Pillar(int gan, int zhi) {
        this.gan = Gan.of(gan);
        this.zhi = Zhi.of(zhi);
    }

    public Gan getGan() {
        return gan;
    }

    public Zhi getZhi() {
        return zhi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var pillar = (Pillar) o;
        return Objects.equals(gan, pillar.gan) && Objects.equals(zhi, pillar.zhi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gan, zhi);
    }

    @Override
    public String toString() {
        return gan + "" + zhi;
    }
}
