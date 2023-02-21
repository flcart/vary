package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:23
 */
public class ScriptDataLessthanSign implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        switch (r.consume()) {
            case '/' -> {
                t.createTempBuffer();
                t.transition("ScriptDataEndTagOpen");
            }
            case '!' -> {
                t.emit("<!");
                t.transition("ScriptDataEscapeStart");
            }
            case eof -> {
                t.emit("<");
                t.eofError(this);
                t.transition("Data");
            }
            default -> {
                t.emit("<");
                r.unconsume();
                t.transition("ScriptData");
            }
        }
    }
}
