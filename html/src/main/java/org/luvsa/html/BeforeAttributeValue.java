package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:52
 */
public class BeforeAttributeValue implements CharHandler {
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
            case '"':
                t.transition("AttributeValue_doubleQuoted");
                break;
            case '\'':
                t.transition("AttributeValue_singleQuoted");
                break;
            case nullChar:
                t.error(this);
                t.tagPending.appendAttributeValue(replacementChar);
                t.transition("AttributeValue_unquoted");
                break;
            case eof:
                t.eofError(this);
                t.emitTagPending();
                t.transition("Data");
                break;
            case '>':
                t.error(this);
                t.emitTagPending();
                t.transition("Data");
                break;
            case '<':
            case '=':
            case '`':
                t.error(this);
                t.tagPending.appendAttributeValue(c);
                t.transition("AttributeValue_unquoted");
                break;
            case '&':
            default:
                r.unconsume();
                t.transition("AttributeValue_unquoted");
        }
    }
}
