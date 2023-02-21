package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:27
 */
public class ScriptDataEscapedDash implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition("Data");
            return;
        }
        var c = r.consume();
        switch (c) {
            case '-' -> {
                t.emit(c);
                t.transition("ScriptDataEscapedDashDash");
            }
            case '<' -> t.transition("ScriptDataEscapedLessthanSign");
            case nullChar -> {
                t.error(this);
                t.emit(replacementChar);
                t.transition("ScriptDataEscaped");
            }
            default -> {
                t.emit(c);
                t.transition("ScriptDataEscaped");
            }
        }
    }
}
