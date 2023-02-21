package org.luvsa.html;


/**
 * @author Aglet
 * @create 2023/2/17 14:43
 */
public class Data implements CharHandler {

    @Override
    public void read(Tokenizer t, CharReader r) {
        switch (r.current()) {
            case '&' -> t.advanceTransition("CharacterReferenceInData");
            case '<' -> t.advanceTransition("TagOpen");
            case nullChar -> {
                t.error(this); // NOT replacement character (oddly?)
                t.emit(r.consume());
            }
            case eof -> t.emit(new Token.EOF());
            default -> t.emit(r.consumeData());
        }
    }
}
