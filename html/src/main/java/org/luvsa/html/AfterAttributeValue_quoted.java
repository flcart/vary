package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:58
 */
public class AfterAttributeValue_quoted implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        char c = r.consume();
        switch (c) {
            case '\t', '\n', '\r', '\f', ' ' -> t.transition("BeforeAttributeName");
            case '/' -> t.transition("SelfClosingStartTag");
            case '>' -> {
                t.emitTagPending();
                t.transition("Data");
            }
            case eof -> {
                t.eofError(this);
                t.transition("Data");
            }
            default -> {
                r.unconsume();
                t.error(this);
                t.transition("BeforeAttributeName");
            }
        }
    }
}
