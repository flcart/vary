package org.luvsa.vary;

/**
 * 字符串工具
 *
 * @author Dale
 * @create 2022/4/29 0:17
 */
public abstract class Strings {

	/**
	 * 小写首字母
	 *
	 * @param str 字符串
	 * @return 小写首字母的字符串
	 */
	public static String uncapitalize(String str) {
		return changeFirstChar(str, false);
	}

	/**
	 * 转换字符串的第一个字母
	 *
	 * @param str        字符串
	 * @param capitalize 是否大写首字母
	 * @return 转换首字母后的字符串
	 */
	private static String changeFirstChar(String str, boolean capitalize) {
		if (str == null || str.isBlank()) {
			return str;
		}

		var first = str.charAt(0);
		var update = capitalize ? Character.toUpperCase(first) : Character.toLowerCase(first);
		if (first == update) {
			// 没有改动
			return str;
		}

		var chars = str.toCharArray();
		chars[0] = update;
		return new String(chars);
	}

	/**
	 * 大写首字母
	 *
	 * @param str 字符串
	 * @return 大写首字母后的字符串
	 */
	public static String capitalize(String str) {
		return changeFirstChar(str, true);
	}
}
