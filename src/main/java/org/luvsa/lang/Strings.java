package org.luvsa.lang;

/**
 * 字符串工具
 *
 * @author Dale
 * @create 2022/4/29 0:17
 */
public final class Strings {

	private Strings() {
		throw new AssertionError("No org.luvsa.vary.Strings instances for you!");
	}

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

	/**
	 * 检测字符串是否全是数字(包括小数点， 即如果含有小数点，也会返回false)
	 *
	 * @param text 需检测的字符串
	 * @return true ： 字符串为一个数字； false ： 不是数字
	 */
	public static boolean isNumber(String text) {
		if (text == null || text.isBlank()) {
			return false;
		}
		// 1. 去掉正负号
		var first = text.charAt(0);
		if (first == '-' || first == '+') {
			text = text.substring(1);
		}
		if (text.isEmpty()) {
			return false;
		}
		// 小数点的位置， 如果没有 index = -1
		for (int i = 0, size = text.length(),  index = text.indexOf('.'); i < size; i++) {
			if (i == index) {// 跳过小数点位置的校验
				continue;
			}
			var c = text.charAt(i);
			if (!Character.isDigit(c)) {
				// 出现非数值
				return false;
			}
		}
		return true;
	}
}
