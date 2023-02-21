package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:14
 */
public class CommentStartDash implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        char c = r.consume();
        switch (c) {
            case '-' -> t.transition("CommentEnd");
            case nullChar -> {
                t.error(this);
                t.commentPending.append(replacementChar);
                t.transition("Comment");
            }
            case '>' -> {
                t.error(this);
                t.emitCommentPending();
                t.transition("Data");
            }
            case eof -> {
                t.eofError(this);
                t.emitCommentPending();
                t.transition("Data");
            }
            default -> {
                t.commentPending.append(c);
                t.transition("Comment");
            }
        }
    }
}
