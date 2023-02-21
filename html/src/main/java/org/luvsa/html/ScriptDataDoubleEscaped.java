package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:43
 */
public class ScriptDataDoubleEscaped implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        var c = r.current();
        switch (c) {
            case '-' -> {
                t.emit(c);
                t.advanceTransition("ScriptDataDoubleEscapedDash");
            }
            case '<' -> {
                t.emit(c);
                t.advanceTransition("ScriptDataDoubleEscapedLessthanSign");
            }
            case nullChar -> {
                t.error(this);
                r.advance();
                t.emit(replacementChar);
            }
            case eof -> {
                t.eofError(this);
                t.transition("Data");
            }
            default -> {
                String data = r.consumeToAny('-', '<', nullChar);
                t.emit(data);
            }
        }
    }
}
