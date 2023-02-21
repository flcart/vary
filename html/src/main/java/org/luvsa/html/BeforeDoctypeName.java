package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:22
 */
public class BeforeDoctypeName implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matchesAsciiAlpha()) {
            t.createDoctypePending();
            t.transition("DoctypeName");
            return;
        }
        char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\r':
            case '\f':
            case ' ':
                break; // ignore whitespace
            case nullChar:
                t.error(this);
                t.createDoctypePending();
                t.doctypePending.name.append(replacementChar);
                t.transition("DoctypeName");
                break;
            case eof:
                t.eofError(this);
                t.createDoctypePending();
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition("Data");
                break;
            default:
                t.createDoctypePending();
                t.doctypePending.name.append(c);
                t.transition("DoctypeName");
        }
    }
}
