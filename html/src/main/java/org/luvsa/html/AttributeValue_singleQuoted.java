package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:56
 */
public class AttributeValue_singleQuoted implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        String value = r.consumeAttributeQuoted(true);
        if (value.length() > 0)
            t.tagPending.appendAttributeValue(value);
        else
            t.tagPending.setEmptyAttributeValue();

        char c = r.consume();
        switch (c) {
            case '\'' -> t.transition("AfterAttributeValue_quoted");
            case '&' -> {
                int[] ref = t.consumeCharacterReference('\'', true);
                if (ref != null)
                    t.tagPending.appendAttributeValue(ref);
                else
                    t.tagPending.appendAttributeValue('&');
            }
            case nullChar -> {
                t.error(this);
                t.tagPending.appendAttributeValue(replacementChar);
            }
            case eof -> {
                t.eofError(this);
                t.transition("Data");
            }
            default -> // hit end of buffer in first read, still in attribute
                    t.tagPending.appendAttributeValue(c);
        }
    }
}
