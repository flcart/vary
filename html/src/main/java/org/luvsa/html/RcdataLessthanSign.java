package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:01
 */
public class RcdataLessthanSign implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        if (r.matches('/')) {
            t.createTempBuffer();
            t.advanceTransition("RCDATAEndTagOpen");
        } else if (r.matchesAsciiAlpha() && t.appropriateEndTagName() != null && !r.containsIgnoreCase(t.appropriateEndTagSeq())) {
            // diverge from spec: got a start tag, but there's no appropriate end tag (</title>), so rather than
            // consuming to EOF; break out here
            t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
            t.emitTagPending();
            t.transition("TagOpen"); // straight into TagOpen, as we came from < and looks like we're on a start tag
        } else {
            t.emit("<");
            t.transition("Rcdata");
        }
    }
}
