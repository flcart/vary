package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:26
 */
public class ScriptDataEscapeStartDash implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition("ScriptDataEscapedDashDash");
        } else {
            t.transition("ScriptData");
        }
    }
}
