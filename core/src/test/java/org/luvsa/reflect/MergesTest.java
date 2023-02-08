package org.luvsa.reflect;

import org.junit.jupiter.api.Test;
import org.luvsa.sys.Userinfo;

import java.util.UUID;

/**
 * @author Aglet
 * @create 2022/12/16 9:45
 */
class MergesTest {

    @Test
    void check() {
        var a = new Userinfo();
        a.setAdmin(true);
        a.setEmail("498971746@qq.com");
        a.setName("张三");
        a.setGuid(UUID.randomUUID().toString());

        var b = new Userinfo();
        b.setName("李四");

        if (Merges.check(a, b)){
            System.out.println(a);
        } else {
            System.out.println(b);
        }
    }
}