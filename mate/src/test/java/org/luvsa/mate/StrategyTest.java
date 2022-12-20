package org.luvsa.mate;

import org.junit.jupiter.api.Test;

/**
 * @author Aglet
 * @create 2022/12/20 9:40
 */
class StrategyTest {

    @Test
    void mobile() {
        var strategy = new MobileStrategy();
        var function = strategy.get();
        var apply = function.apply("18874489035");
        System.out.println(apply);
    }

    @Test
    void email(){
        var strategy = new EmailStrategy();
        var function = strategy.get();
        var apply = function.apply("498971746@qq.com");
        System.out.println(apply);
    }
}