package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:39
 */
public class ScriptDataEscapedDashDash implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition("Data");
            return;
        }

        char c = r.consume();
        switch (c) {
            case '-' -> t.emit(c);
            case '<' -> t.transition("ScriptDataEscapedLessthanSign");
            case '>' -> {
                t.emit(c);
                t.transition("ScriptData");
            }
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
