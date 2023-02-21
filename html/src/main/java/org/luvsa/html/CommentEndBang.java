package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:19
 */
public class CommentEndBang implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        char c = r.consume();
        switch (c) {
            case '-' -> {
                t.commentPending.append("--!");
                t.transition("CommentEndDash");
            }
            case '>' -> {
                t.emitCommentPending();
                t.transition("Data");
            }
            case nullChar -> {
                t.error(this);
                t.commentPending.append("--!").append(replacementChar);
                t.transition("Comment");
            }
            case eof -> {
                t.eofError(this);
                t.emitCommentPending();
                t.transition("Data");
            }
            default -> {
                t.commentPending.append("--!").append(c);
                t.transition("Comment");
            }
        }
    }
}
