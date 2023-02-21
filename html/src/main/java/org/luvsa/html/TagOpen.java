package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 15:23
 */
public class TagOpen implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        switch (r.current()) {
            case '!' -> t.advanceTransition("MarkupDeclarationOpen");
            case '/' -> t.advanceTransition("EndTagOpen");
            case '?' -> {
                t.createBogusCommentPending();
                t.transition("BogusComment");
            }
            default -> {
                if (r.matchesAsciiAlpha()) {
                    t.createTagPending(true);
                    t.transition("TagName");
                } else {
                    t.error(this);
                    t.emit('<'); // char that got us here
                    t.transition("Data");
                }
            }
        }
    }
}
