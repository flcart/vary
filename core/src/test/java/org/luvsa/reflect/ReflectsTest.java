package org.luvsa.reflect;

import org.junit.jupiter.api.Test;
import org.luvsa.sys.Userinfo;

import java.util.UUID;

/**
 * @author Aglet
 * @create 2023/3/18 11:35
 */
class ReflectsTest {

    @Test
    void asMap() {
        var a = new Userinfo();
        a.setAdmin(true);
        a.setEmail("498971746@qq.com");
        a.setName("张三");
        a.setGuid(UUID.randomUUID().toString());

        var map = Reflects.asMap(a);

        System.out.println(map);

    }
}