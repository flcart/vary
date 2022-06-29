package org.luvsa.vary;

/**
 * @author Dale
 * @create 2022/4/29 0:17
 */
public class Strings {

    public static String uncapitalize(String str) {
        return changeFirstChar(str, false);
    }

    private static String changeFirstChar(String str, boolean capitalize) {
        if (str == null || str.isBlank()) {
            return str;
        }
        var baseChar = str.charAt(0);
        var updatedChar =  capitalize ?  Character.toUpperCase(baseChar) :
                Character.toLowerCase(baseChar) ;

        if (baseChar == updatedChar) {
            return str;
        }
        var chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars);
    }

	public static String capitalize(String str) {
        return changeFirstChar(str, true);
	}
}
