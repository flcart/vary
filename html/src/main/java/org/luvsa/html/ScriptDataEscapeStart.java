package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:25
 */
public class ScriptDataEscapeStart implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matches('-')) {
            t.emit('-');
            t.advanceTransition("ScriptDataEscapeStartDash");
        } else {
            t.transition("ScriptData");
        }
    }

}
