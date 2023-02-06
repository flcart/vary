package org.luvsa.lang;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 文件格式编码
 *
 * @author Aglet
 * @create 2023/1/31 11:12
 */
public class Charsets {

    private final static Charset GBK = Charset.forName("GBK");

    private final static Map<String, Charset> map = Map.of(
            "EFBBBF", StandardCharsets.UTF_8,
            "FEFF", StandardCharsets.UTF_16BE,
            "FFFE", StandardCharsets.UTF_16
    );

    private static final char[] ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static Charset guess(byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return Charset.defaultCharset();
        }
        //如果含有bom头信息的话
        var string = binary(Arrays.copyOf(bytes, Math.min(bytes.length, 6)));
        var upper = string.toUpperCase();
        var entries = map.entrySet();
        for (var entry : entries) {
            var key = entry.getKey().toUpperCase();
            if (upper.startsWith(key)) {
                return entry.getValue();
            }
        }
        //没有bom头信息的话
        for (byte bt : bytes) {
            var hex = byteToHex(bt);
            //忽略ASCII码部分
            int btInt = Integer.parseInt(hex, 16);
            if (btInt < 128 && btInt >= 0) {
                continue;
            }
            upper = hex.toUpperCase();
            if (upper.startsWith("E")) {
                return StandardCharsets.UTF_8;
            }
            return GBK;
        }
        return GBK;
    }


    /**
     * 将单个byte转换为16进制表示的字符串
     *
     * @param b 字节
     * @return 字符串
     */
    private static String byteToHex(byte b) {
        var buf = new char[2];
        var a = b < 0 ? 256 + b : b;
        buf[0] = ARRAY[a / 16];
        buf[1] = ARRAY[a % 16];
        return new String(buf);
    }

    /**
     * 将字节码数组转换为指定的进制字符串
     *
     * @param bytes 字节码数组
     * @return 字符串
     */
    private static String binary(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        var size = bytes.length;
        var buf = new char[size << 1];
        for (int i = 0, a, j = 0; i < size; i++) {
            var b = bytes[i];
            a = b < 0 ? 256 + b : b;
            buf[j++] = ARRAY[a / 16];
            buf[j++] = ARRAY[a % 16];
        }
        return new String(buf);
    }
}
