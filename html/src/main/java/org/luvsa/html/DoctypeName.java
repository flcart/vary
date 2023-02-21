package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:23
 */
public class DoctypeName implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.doctypePending.name.append(name);
            return;
        }
        char c = r.consume();
        switch (c) {
            case '>' -> {
                t.emitDoctypePending();
                t.transition("Data");
            }
            case '\t', '\n', '\r', '\f', ' ' -> t.transition("AfterDoctypeName");
            case nullChar -> {
                t.error(this);
                t.doctypePending.name.append(replacementChar);
            }
            case eof -> {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition("Data");
            }
            default -> t.doctypePending.name.append(c);
        }
    }
}
