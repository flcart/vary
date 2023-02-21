package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:02
 */
public class MarkupDeclarationOpen implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matchConsume("--")) {
            t.createCommentPending();
            t.transition("CommentStart");
        } else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
            t.transition("Doctype");
        } else if (r.matchConsume("[CDATA[")) {
            // todo: should actually check current namespace, and only non-html allows cdata. until namespace
            // is implemented properly, keep handling as cdata
            //} else if (!t.currentNodeInHtmlNS() && r.matchConsume("[CDATA[")) {
            t.createTempBuffer();
            t.transition("CdataSection");
        } else {
            t.error(this);
            t.createBogusCommentPending();
            t.transition("BogusComment");
        }
    }
}
