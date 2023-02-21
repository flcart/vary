package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:46
 */
public class BeforeAttributeName implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\r':
            case '\f':
            case ' ':
                break;
            case '/':
                t.transition("SelfClosingStartTag");
                break;
            case '<':
                r.unconsume();
                t.error(this);
            case '>':
                t.emitTagPending();
                t.transition("Data");
                break;
            case nullChar:
                r.unconsume();
                t.error(this);
                t.tagPending.newAttribute();
                t.transition("AttributeName");
                break;
            case eof:
                t.eofError(this);
                t.transition("Data");
                break;
            case '"':
            case '\'':
            case '=':
                t.error(this);
                t.tagPending.newAttribute();
                t.tagPending.appendAttributeName(c);
                t.transition("AttributeName");
                break;
            default:
                t.tagPending.newAttribute();
                r.unconsume();
                t.transition("AttributeName");
        }
    }
}
