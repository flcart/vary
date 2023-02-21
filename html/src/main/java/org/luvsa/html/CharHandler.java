package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 14:43
 */
public interface CharHandler {

    static final char[] attributeNameCharsSorted = new char[]{'\t', '\n', '\f', '\r', ' ', '"', '\'', '/', '<', '=', '>'};
    static final char nullChar = '\u0000';
    static final char eof = CharReader.EOF;
    static final char replacementChar = Tokenizer.replacementChar;
     static final String replacementStr = String.valueOf(replacementChar);

    static final char[] attributeValueUnquoted = new char[]{nullChar, '\t', '\n', '\f', '\r', ' ', '"', '&', '\'', '<', '=', '>', '`'};
    void read(Tokenizer tokenizer, CharReader reader);

    static void readCharRef(Tokenizer t, String key) {
        var c = t.consumeCharacterReference(null, false);
        if (c == null)
            t.emit('&');
        else
            t.emit(c);

        t.transition(key);
    }

    static void readRawData(Tokenizer t, CharReader r, CharHandler current, String advance) {
        switch (r.current()) {
            case '<' -> t.advanceTransition(advance);
            case nullChar -> {
                t.error(current);
                r.advance();
                t.emit(replacementChar);
            }
            case eof -> t.emit(new Token.EOF());
            default -> t.emit(r.consumeRawData());
        }
    }

    static void readEndTag(Tokenizer t, CharReader r, String a, String b) {
        if (r.matchesAsciiAlpha()) {
            t.createTagPending(false);
            t.transition(a);
        } else {
            t.emit("</");
            t.transition(b);
        }
    }

     static void handleDataEndTag(Tokenizer t, CharReader r, String elseTransition) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name);
            t.dataBuffer.append(name);
            return;
        }

        boolean needsExitTransition = false;
        if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
            char c = r.consume();
            switch (c) {
                case '\t', '\n', '\r', '\f', ' ' -> t.transition("BeforeAttributeName");
                case '/' -> t.transition("SelfClosingStartTag");
                case '>' -> {
                    t.emitTagPending();
                    t.transition("Data");
                }
                default -> {
                    t.dataBuffer.append(c);
                    needsExitTransition = true;
                }
            }
        } else {
            needsExitTransition = true;
        }

        if (needsExitTransition) {
            t.emit("</");
            t.emit(t.dataBuffer);
            t.transition(elseTransition);
        }
    }

    static void handleDataDoubleEscapeTag(Tokenizer t, CharReader r, String primary, String fallback) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.dataBuffer.append(name);
            t.emit(name);
            return;
        }

        char c = r.consume();
        switch (c) {
            case '\t', '\n', '\r', '\f', ' ', '/', '>' -> {
                if (t.dataBuffer.toString().equals("script"))
                    t.transition(primary);
                else
                    t.transition(fallback);
                t.emit(c);
            }
            default -> {
                r.unconsume();
                t.transition(fallback);
            }
        }
    }
}
