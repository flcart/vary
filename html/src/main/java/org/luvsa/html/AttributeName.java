package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:48
 */
public class AttributeName implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        var name = r.consumeToAnySorted(attributeNameCharsSorted); // spec deviate - consume and emit nulls in one hit vs stepping
        t.tagPending.appendAttributeName(name);

        char c = r.consume();
        switch (c) {
            case '\t', '\n', '\r', '\f', ' ' -> t.transition("AfterAttributeName");
            case '/' -> t.transition("SelfClosingStartTag");
            case '=' -> t.transition("BeforeAttributeValue");
            case '>' -> {
                t.emitTagPending();
                t.transition("Data");
            }
            case eof -> {
                t.eofError(this);
                t.transition("Data");
            }
            case '"', '\'', '<' -> {
                t.error(this);
                t.tagPending.appendAttributeName(c);
            }
            default -> // buffer underrun
                    t.tagPending.appendAttributeName(c);
        }
    }
}
