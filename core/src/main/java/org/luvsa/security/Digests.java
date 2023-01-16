package org.luvsa.security;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * 数据 md5 加密工具， 用于加密 字符串
 *
 * @author Aglet
 * @create 2022/10/20 17:25
 */

public final class Digests {

    private Digests() {
        throw new AssertionError("No com.jdy.angel.utils.Digests instances for you!");
    }

    /**
     * 将 字符串数组 md5 加密
     *
     * @param array 字符串树
     * @return 加密后的字符串
     */
    public static String md5AsHex(CharSequence... array) {
        var join = String.join("", array);
        var bytes = join.getBytes(StandardCharsets.UTF_8);
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /**
     * 将 uuid 采用md5加密
     *
     * @param joiner UUID
     * @return 加密的字符串
     */
    public static String md5AsHex(StringJoiner joiner) {
        var bytes = joiner.toString().getBytes(StandardCharsets.UTF_8);
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /**
     * 将 uuid 采用md5加密
     *
     * @param uuid UUID
     * @return 加密的字符串
     */
    public static String md5AsHex(UUID uuid) {
        var bytes = uuid.toString().getBytes(StandardCharsets.UTF_8);
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
