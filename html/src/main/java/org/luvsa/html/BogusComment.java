package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:00
 */
public class BogusComment implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        // todo: handle bogus comment starting from eof. when does that trigger?
        t.commentPending.append(r.consumeTo('>'));
        // todo: replace nullChar with replaceChar
        char next = r.current();
        if (next == '>' || next == eof) {
            r.consume();
            t.emitCommentPending();
            t.transition("Data");
        }
    }
}
