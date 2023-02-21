package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:15
 */
public class Comment implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        char c = r.current();
        switch (c) {
            case '-' -> t.advanceTransition("CommentEndDash");
            case nullChar -> {
                t.error(this);
                r.advance();
                t.commentPending.append(replacementChar);
            }
            case eof -> {
                t.eofError(this);
                t.emitCommentPending();
                t.transition("Data");
            }
            default -> t.commentPending.append(r.consumeToAny('-', nullChar));
        }
    }
}
