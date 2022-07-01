# vary

数据转换工具， 可支持：

#### String 类型转其他数据类型

###### 转 `java.util.Date`

```java
@ParameterizedTest
@ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
void strToDate(String txt) {
    Date date = Vary.change(txt, Date.class);
    System.out.println(date);
}
```

###### 转 `java.lang.Boolean`

```java
@ParameterizedTest
@ValueSource(strings = {"1", "ok", "on", "true", "up", "y", "yes", "对", "是", "男", "真", "2002/6/28", "2002年12月1日 15:30"})
void strToBool(String txt) {
    Boolean bool = Vary.change(txt, Boolean.class);
    System.out.println(bool);
}
```

###### 转 `java.time.LocalDate`

```java
@ParameterizedTest
@ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
void strToLocalDate(String txt) {
    LocalDate date = Vary.change(txt, LocalDate.class);
    System.out.println(date);
}
```

###### 转 `java.time.LocalDateTime`

```java
@ParameterizedTest
@ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30"})
void strToLocalDateTime(String txt) {
    LocalDateTime dateTime = Vary.change(txt, LocalDateTime.class);
    System.out.println(dateTime);
}
```

###### 转 `java.time.LocalTime`

```java
@ParameterizedTest
@ValueSource(strings = {"2002/6/28", "2002年12月1日 15:30", "12:51", "12:51:26", "12:51:26:333"})
void strToLocalTime(String txt) {
    LocalTime tiem = Vary.change(txt, LocalTime.class);
    System.out.println(tiem);
}
```

###### 转 `java.lang.Number`

```java
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

    System.out.println(aByte);
}
```





