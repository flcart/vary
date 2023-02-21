package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 10:20
 */
public class Doctype implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        var c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\r':
            case '\f':
            case ' ':
                t.transition("BeforeDoctypeName");
                break;
            case eof:
                t.eofError(this);
                // note: fall through to > case
            case '>': // catch invalid <!DOCTYPE>
                t.error(this);
                t.createDoctypePending();
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition("Data");
                break;
            default:
                t.error(this);
                t.transition("BeforeDoctypeName");
        }
    }
}
