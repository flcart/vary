package org.luvsa.vary;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * @author Aglet
 * @create 2022/11/14 14:38
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws ParseException {
        var format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.ENGLISH);
        var parse = format.parse("Sun Dec 01 15:30:00 CST 2002");

        var text = format.format(new Date());

        System.out.println(text);
//        var aClass = String[].class;
//        System.out.println(aClass);
    }
}
