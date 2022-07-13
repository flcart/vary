package org.luvsa.vary;


import org.luvsa.vary.other.Iob;
import org.luvsa.vary.other.SupportIob;

/**
 * @author Aglet
 * @create 2022/7/5 17:28
 */
@SupportIob
public interface Item {

    @Iob("序号")
    int getGuid();

    @Iob("名称")
    String getName();

    @Iob("数据")
    String getText();
}
