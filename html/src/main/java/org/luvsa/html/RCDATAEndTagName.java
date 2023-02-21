package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:09
 */
public class RCDATAEndTagName implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matchesAsciiAlpha()) {
            var name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
            return;
        }

        char c = r.consume();
        switch (c) {
            case '\t', '\n', '\r', '\f', ' ' -> {
                if (t.isAppropriateEndTagToken())
                    t.transition("BeforeAttributeName");
                else
                    anythingElse(t, r);
            }
            case '/' -> {
                if (t.isAppropriateEndTagToken())
                    t.transition("SelfClosingStartTag");
                else
                    anythingElse(t, r);
            }
            case '>' -> {
                if (t.isAppropriateEndTagToken()) {
                    t.emitTagPending();
                    t.transition("Data");
                } else
                    anythingElse(t, r);
            }
            default -> anythingElse(t, r);
        }
    }

    private void anythingElse(Tokenizer t, CharReader r) {
        t.emit("</");
        t.emit(t.dataBuffer);
        r.unconsume();
        t.transition("Rcdata");
    }
}
