package org.luvsa.mate;

/**
 * @author Aglet
 * @create 2022/12/19 17:24
 */
public class Strategies {
    public final static String MOBILE = "mobile";
    public final static String EMAIL = "email";

    /**
     * 身份证号码
     */
    public final static String ID = "email";

    public static String guess(String name) {
        var chars = name.toCharArray();
        for (int i = 0, j = 0, size = chars.length; i < size; i++) {
            var c = chars[i];
            if (Character.isUpperCase(c)) {
                if (j != 0) {
                    var sub = name.substring(0, i);
                    return sub.toLowerCase();
                }
                j++;
            }
        }
        return name;
    }
}
