package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:45
 */
public class ScriptDataDoubleEscapedLessthanSign implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matches('/')) {
            t.emit('/');
            t.createTempBuffer();
            t.advanceTransition("ScriptDataDoubleEscapeEnd");
        } else {
            t.transition("ScriptDataDoubleEscaped");
        }
    }
}
