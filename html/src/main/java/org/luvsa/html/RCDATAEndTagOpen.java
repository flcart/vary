package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:07
 */
public class RCDATAEndTagOpen implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.tagPending.appendTagName(r.current());
            t.dataBuffer.append(r.current());
            t.advanceTransition("RCDATAEndTagName");
        } else {
            t.emit("</");
            t.transition("Rcdata");
        }
    }
}
