package org.luvsa.vary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.luvsa.vary.other.ToMap.Iob;
import org.luvsa.vary.other.ToMap.SupportIob;
import org.luvsa.vary.other.DynamicProxy.MethodName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Aglet
 * @create 2022/6/29 17:34
 */
class VaryTest {

    @ParameterizedTest
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
    void strToDate(String txt) {
        Date date = Vary.change(txt, Date.class);
        System.out.println(date);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
    void strToLocalDate(String txt) {
        LocalDate date = Vary.change(txt, LocalDate.class);
        System.out.println(date);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
    void strToLocalDateTime(String txt) {
        LocalDateTime dateTime = Vary.change(txt, LocalDateTime.class);
        System.out.println(dateTime);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30", "12:51", "12:51:26", "12:51:26:333"})
    void strToLocalTime(String txt) {
        LocalTime time = Vary.change(txt, LocalTime.class);
        System.out.println(time);
    }

    @ParameterizedTest
    @ValueSource(strings = {"50", "72.89", "九亿零八百零八万零六百零三", "三", "十一亿零五百万", "十", "十七", "五十三", "一百零八", "九百五十", "三千零五十四", "一万零三", "七千八百六十三万九千五百八十八", "十万", "一万零三"})
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


    @ParameterizedTest
    @ValueSource(strings = {"1", "ok", "on", "true", "up", "y", "yes", "对", "是", "男", "真", "2002/6/28", "2002年12月1日 15:30"})
    void strToBool(String txt) {
        Boolean bool = Vary.change(txt, Boolean.class);
        System.out.println(bool);
    }

    @Test
    void strToList() {
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
        var future = Vary.change(talent, Tuple.class);
        var change = Vary.change(future, Map.class);
        System.out.println(change);
    }


    @SupportIob
    public interface Future {

        @Iob(value = "P值")
        int getPoint();

        @Iob(value = "含义")
        @MethodName(value = "getText", code = "s.split(\"\n\");", generics = true)
        List<Item> getMeans();

        @Iob(value = "时间")
        default Time getTime() {
            return new Time();
        }
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
}