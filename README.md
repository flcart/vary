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

