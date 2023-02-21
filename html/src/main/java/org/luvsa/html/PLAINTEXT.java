package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 15:48
 */
public class PLAINTEXT implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        switch (r.current()) {
            case nullChar -> {
                t.error(this);
                r.advance();
                t.emit(replacementChar);
            }
            case eof -> t.emit(new Token.EOF());
            default -> t.emit(r.consumeTo(nullChar));
        }
    }
}
