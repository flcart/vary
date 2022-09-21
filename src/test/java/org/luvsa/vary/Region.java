package org.luvsa.vary;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Region<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2848288447214773183L;

    /**
     * 起始位置
     */
    private T from;

    /**
     * 结束位置
     */
    private T to;

    public Region() {
    }

    public Region(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public Region<LocalDateTime> asLocalDataTime() {
        var region = new Region<LocalDateTime>();
        region.setFrom(Vary.change(from, LocalDateTime.class));
        region.setTo(Vary.change(to, LocalDateTime.class));
        return region;
    }
}
