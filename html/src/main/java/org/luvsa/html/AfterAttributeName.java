package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:51
 */
public class AfterAttributeName implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\r':
            case '\f':
            case ' ':
                // ignore
                break;
            case '/':
                t.transition("SelfClosingStartTag");
                break;
            case '=':
                t.transition("BeforeAttributeValue");
                break;
            case '>':
                t.emitTagPending();
                t.transition("Data");
                break;
            case nullChar:
                t.error(this);
                t.tagPending.appendAttributeName(replacementChar);
                t.transition("AttributeName");
                break;
            case eof:
                t.eofError(this);
                t.transition("Data");
                break;
            case '"':
            case '\'':
            case '<':
                t.error(this);
                t.tagPending.newAttribute();
                t.tagPending.appendAttributeName(c);
                t.transition("AttributeName");
                break;
            default: // A-Z, anything else
                t.tagPending.newAttribute();
                r.unconsume();
                t.transition("AttributeName");
        }
    }
}
