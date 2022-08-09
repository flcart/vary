package org.luvsa.vary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.luvsa.vary.other.Iob;
import org.luvsa.vary.other.SupportIob;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author Aglet
 * @create 2022/6/29 17:34
 */
class VaryTest {

    @ParameterizedTest
    @DisplayName("字符串转 Date")
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
    void strToDate(String txt) {
        var date = Vary.change(txt, Date.class);
        System.out.println(date);
    }

    @ParameterizedTest
    @DisplayName("字符串转 LocalDate")
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
    void strToLocalDate(String txt) {
        var date = Vary.change(txt, LocalDate.class);
        System.out.println(date);
    }

    @ParameterizedTest
    @DisplayName("字符串转 LocalDateTime")
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30", "2022-08-05T00:16:15.987808300"})
    void strToLocalDateTime(String txt) {
        var dateTime = Vary.change(txt, LocalDateTime.class);
        System.out.println(dateTime);
    }

    @Test
    @DisplayName("字符串转 LocalDateTime")
//    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30", "2022-08-05T00:16:15.987808300"})
    void strToLocalDateTime1() {
//        var txt = "2022-08-05T00:16:15.987808300";
//        var formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//        var parse = LocalDateTime.parse(txt, formatter);
        var dateTime = Vary.change("2022-08-05T00:16:15.987808300", LocalDateTime.class);
        System.out.println(dateTime);
    }

    @ParameterizedTest
    @DisplayName("字符串转 LocalTime")
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30", "12:51", "12:51:26", "12:51:26:333"})
    void strToLocalTime(String txt) {
        var time = Vary.change(txt, LocalTime.class);
        System.out.println(time);
    }

    @ParameterizedTest
    @DisplayName("字符串转数字")
    @ValueSource(strings = {"两百", "二百", "50", "72.89", "九亿零八百零八万零六百零三", "三", "十一亿零五百万", "十", "十七", "五十三", "一百零八", "九百五十", "三千零五十四", "一万零三", "七千八百六十三万九千五百八十八", "十万", "一万零三"})
    void strToNumber(String txt) {
        var aShort = Vary.change(txt, short.class);
        var bShort = Vary.change(txt, Short.class);

        var aByte = Vary.change(txt, Byte.class);
        var bByte = Vary.change(txt, byte.class);

        var aInteger = Vary.change(txt, int.class);
        var bInteger = Vary.change(txt, Integer.class);

        var aLong = Vary.change(txt, long.class);
        var bLong = Vary.change(txt, Long.class);

        var aFloat = Vary.change(txt, float.class);
        var bFloat = Vary.change(txt, Float.class);

        var aDouble = Vary.change(txt, Double.class);
        var bDouble = Vary.change(txt, double.class);

        System.out.println(bDouble);
    }

    @Test
    void strToNum() {
        var a = Vary.change("", int.class);
        var b = Vary.apply("", 0);
        var time = Vary.change("", LocalTime.class);
        var change = Vary.change(66, char.class);
        System.out.println(change);
    }


    @ParameterizedTest
    @ValueSource(strings = {"1", "ok", "on", "true", "up", "y", "yes", "对", "是", "男", "真", "2002/6/28", "2002年12月1日 15:30"})
    @DisplayName("字符串转Boolean")
    void strToBool(String txt) {
        var bool = Vary.change(txt, Boolean.class);
        System.out.println(bool);
    }

    @ParameterizedTest
    @DisplayName("时间戳 转 LocalDate")
    @ValueSource(longs = {0L, 1657109858000L, 946656000000L})
    void longToLocalDate(long l) {
        var date = Vary.change(l, LocalDate.class);
        System.out.println(date);
    }

    @ParameterizedTest
    @DisplayName("时间戳 转 LocalDateTime")
    @ValueSource(longs = {1657109858000L, 946656000000L, 0L})
    void longToLocalDateTime(long l) {
        var date = Vary.change(l, LocalDateTime.class);
        var b = Vary.change(null, LocalDateTime.class);
        System.out.println(date);
    }

    @Test
    @DisplayName("LocalDateTime  转 时间戳")
    void localDateTimeToLong() {
        var now = LocalDateTime.now();
        var s = now.toString();
        System.out.println(s);
        var b = Vary.change(now, long.class);
        var change = Vary.change(s, LocalDateTime.class);
        System.out.println(b);
        System.out.println(change);
    }

    @ParameterizedTest
    @DisplayName("时间戳 转 LocalTime")
    @ValueSource(longs = {0L, 1657109858000L, 946656000000L})
    void longToLocalTime(long l) {
        var date = Vary.change(l, LocalTime.class);
        System.out.println(date);
    }

    @Test
    void dateToLocal() {
        var date = new Date();
        var change = Vary.change(date, LocalDateTime.class);
        var other = Vary.change(date, long.class);
        System.out.println(change);
        System.out.println(other);
    }

    @Test
    void localToDate() {
        var change = Vary.change(LocalDateTime.now(), Date.class);
        System.out.println(change);
    }

    @Test
    void toBigDecimal() {
        char[] array0 = Vary.change("char", char[].class);
        Character[] array1 = Vary.change("Character", Character[].class);
        var change = Vary.change(1, BigDecimal.class);
        System.out.println(change);
    }


    @Test
    void numToChar() {
        // lowSurrogate
        //highSurrogate
//        var A = Vary.change(65, char.class);
//        var Z = Vary.change(65, char.class);
//        var c = Vary.change(65, char.class);
//        var a = Vary.change(65, char.class);
//        var b = Vary.change(65, char.class);
//        var c = Vary.change(65, char.class);
        var list = new ArrayList<>();
        for (int i = 0; i < 127; i++) {
            var change = Vary.change(i, char.class);
            list.add(change);
        }
        System.out.println(list);
    }

    @Test
    void proxy() {
        var txt = """
                 1、总论：发展性很小，切勿好高骛远，以免为钱财起纠纷，家庭生活能无忧就该满足，不要不平不满，注重精神生活才是聪明人。
                 2、性格：表面乐观，内心苦闷，常有不平不满之心理，待人诚实而自劳，喜批评人家是非，自己却容易被煽动。
                 3、意志：意志尚称坚定，但缺乏果断力，处事容易冲动，情绪苦闷又不安定。
                 4、事业：一生辛勤而收获不多，常在忧愁中度日，六亲又难相助。
                 5、家庭：与父母意见不和，夫妻表面平顺，但时常争吵，家内不太平安。
                 6、婚姻：男娶勤俭之妻，夫妻常为小事闹意见；女嫁好胜好强之夫，婚姻不太美满。
                 7、子女：子女运佳，责任心重，能在社会上成功。
                 8、社交：待人诚恳有雅量，但要领不佳，常有受累之事发生，少管闲事为妙。
                 9、精神：为朋友之事及金钱问题操心，心性欠开朗。
                 10、财运：与金钱缘薄，纵有积蓄也有限。
                 11、健康：易患胃肠病，筋骨酸痛。神经质。
                 12、老运：运限平稳，但财源不佳，应注重精神修养。
                """;
        var talent = new Talent();
        talent.setText(txt);
        talent.setPoint(20);
        var future = Vary.change(talent, Future.class);
        var point = future.getPoint();
        var means = future.getMeans();
        System.out.println("Point : " + point + ", means : " + means);
    }

    @Test
    void strToList() {

    }


    @Test
    void oto() {
        var prism = new Prism();
        var change = Vary.change(prism, Future.class);
        System.out.println(change);
    }


    @SupportIob
    static class Prism {

        /**
         * @return 天干元素
         */
        @Iob("天干")
        public char getGan() {
            return '甲';
        }

        /**
         * @return 地支元素
         */
        @Iob("地支")
        public char getZhi() {
            return '午';
        }

    }

    static class Time extends Prism {
    }

    public static Time toPrism(Talent talent) {
        return new Time();
    }
}