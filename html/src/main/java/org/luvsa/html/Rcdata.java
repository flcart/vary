package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 15:39
 */
public class Rcdata implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        switch (r.current()) {
            case '&' -> t.advanceTransition("CharacterReferenceInRcdata");
            case '<' -> t.advanceTransition("RcdataLessthanSign");
            case nullChar -> {
                t.error(this);
                r.advance();
                t.emit(replacementChar);
            }
            case eof -> t.emit(new Token.EOF());
            default -> t.emit(r.consumeData());
        }
    }
}
