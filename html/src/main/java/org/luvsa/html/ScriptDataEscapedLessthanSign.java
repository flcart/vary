package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:39
 */
public class ScriptDataEscapedLessthanSign implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matchesAsciiAlpha()) {
            t.createTempBuffer();
            t.dataBuffer.append(r.current());
            t.emit("<");
            t.emit(r.current());
            t.advanceTransition("ScriptDataDoubleEscapeStart");
        } else if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition("ScriptDataEscapedEndTagOpen");
        } else {
            t.emit('<');
            t.transition("ScriptDataEscaped");
        }
    }
}
