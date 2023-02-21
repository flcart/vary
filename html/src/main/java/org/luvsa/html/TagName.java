package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 8:52
 */
public class TagName implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        // previous TagOpen state did NOT consume, will have a letter char in current
        var tagName = r.consumeTagName();
        t.tagPending.appendTagName(tagName);
        char c = r.consume();
        switch (c) {
            case '\t', '\n', '\r', '\f', ' ' -> t.transition("BeforeAttributeName");
            case '/' -> t.transition("SelfClosingStartTag");
            case '<' -> {// NOTE: out of spec, but clear author intent
                r.unconsume();
                t.error(this);
            }
            case '>' -> {
                t.emitTagPending();
                t.transition("Data");
            }
            case nullChar -> t.tagPending.appendTagName(replacementStr);
            case eof -> {
                // should emit pending tag?
                t.eofError(this);
                t.transition("Data");
            }
            default -> // buffer underrun
                    t.tagPending.appendTagName(c);
        }
    }
}
