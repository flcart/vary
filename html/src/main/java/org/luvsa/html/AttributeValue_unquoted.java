package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:57
 */
public class AttributeValue_unquoted implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        String value = r.consumeToAnySorted(attributeValueUnquoted);
        if (value.length() > 0)
            t.tagPending.appendAttributeValue(value);

        char c = r.consume();
        switch (c) {
            case '\t', '\n', '\r', '\f', ' ' -> t.transition("BeforeAttributeName");
            case '&' -> {
                int[] ref = t.consumeCharacterReference('>', true);
                if (ref != null)
                    t.tagPending.appendAttributeValue(ref);
                else
                    t.tagPending.appendAttributeValue('&');
            }
            case '>' -> {
                t.emitTagPending();
                t.transition("Data");
            }
            case nullChar -> {
                t.error(this);
                t.tagPending.appendAttributeValue(replacementChar);
            }
            case eof -> {
                t.eofError(this);
                t.transition("Data");
            }
            case '"', '\'', '<', '=', '`' -> {
                t.error(this);
                t.tagPending.appendAttributeValue(c);
            }
            default -> // hit end of buffer in first read, still in attribute
                    t.tagPending.appendAttributeValue(c);
        }

    }
}
