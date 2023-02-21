package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:26
 */
public class ScriptDataEscaped implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.transition("Data");
            return;
        }

        switch (r.current()) {
            case '-' -> {
                t.emit('-');
                t.advanceTransition("ScriptDataEscapedDash");
            }
            case '<' -> t.advanceTransition("ScriptDataEscapedLessthanSign");
            case nullChar -> {
                t.error(this);
                r.advance();
                t.emit(replacementChar);
            }
            default -> {
                var data = r.consumeToAny('-', '<', nullChar);
                t.emit(data);
            }
        }
    }
}
