package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:14
 */
public class RawtextLessthanSign implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition("RawtextEndTagOpen");
        } else {
            t.emit('<');
            t.transition("Rawtext");
        }
    }
}
