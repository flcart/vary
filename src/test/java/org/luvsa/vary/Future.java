package org.luvsa.vary;

import org.luvsa.vary.other.Iob;
import org.luvsa.vary.proxy.Mapper;

import java.util.List;

/**
 * @author Aglet
 * @create 2022/7/15 10:37
 */
public interface Future {
    @Iob(value = "P值")
    int getPoint();

    @Iob(value = "含义")
    @Mapper(value = "getText", code = "split(\"\n\");")
    List<Item> getMeans();

}
