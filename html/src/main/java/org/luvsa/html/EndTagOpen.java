package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 15:50
 */
public class EndTagOpen implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.isEmpty()) {
            t.eofError(this);
            t.emit("</");
            t.transition("Data");
        } else if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.transition("TagName");
        } else if (r.matches('>')) {
            t.error(this);
            t.advanceTransition("Data");
        } else {
            t.error(this);
            t.createBogusCommentPending();
            t.commentPending.append('/'); // push the / back on that got us here
            t.transition("BogusComment");
        }
    }
}
