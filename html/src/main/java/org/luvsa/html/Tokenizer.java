package org.luvsa.html;

import org.luvsa.annotation.Name;
import org.luvsa.html.Token.Tag;
import org.luvsa.lang.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author Aglet
 * @create 2023/2/17 14:16
 */
public final class Tokenizer {
    static final char replacementChar = '\uFFFD'; // replaces null character
    private final static Map<String, CharHandler> handlers = new HashMap<>();
    Token.Comment commentPending = new Token.Comment();
    Token.StartTag startPending = new Token.StartTag();
    Token.Tag tagPending = startPending;

    Token.Doctype doctypePending = new Token.Doctype();

    StringBuilder dataBuffer = new StringBuilder(1024);
    static {
        var load = ServiceLoader.load(CharHandler.class);
        for (var handler : load) {
            var aClass = handler.getClass();
            var name = aClass.getAnnotation(Name.class);
            if (name == null || Strings.isEmpty(name.value())) {
                handlers.put(aClass.getSimpleName(), handler);
            } else {
                handlers.put(name.value(), handler);
            }
        }
    }

    private CharHandler handler = handlers.get("Data");

    private CharReader reader;

    private boolean pend = false;

    public Token next() {
        while (!pend) {
            handler.read(this, reader);
        }
        return null;
    }

    public void advanceTransition(String key) {
        this.handler = handlers.get(key);
    }

    public void error(Data data) {
//        if (errors.canAddError())
//            errors.add(new ParseError(reader, "Unexpected character '%s' in input state [%s]", reader.current(), state));
    }

    public void error(CharHandler handler) {
//        if (errors.canAddError())
//            errors.add(new ParseError(reader, "Unexpected character '%s' in input state [%s]", reader.current(), state));
    }

    public void emit(char consume) {

    }

    public void emit(int[] codepoints) {
        emit(new String(codepoints, 0, codepoints.length));
    }

    public void emit(Token token) {

//        Validate.isFalse(isEmitPending);
//
//        emitPending = token;
//        isEmitPending = true;
//        token.startPos(markupStartPos);
//        token.endPos(reader.pos());
//        charStartPos = Unset;
//
//        if (token.type == Token.TokenType.StartTag) {
//            Token.StartTag startTag = (Token.StartTag) token;
//            lastStartTag = startTag.tagName;
//            lastStartCloseSeq = null; // only lazy inits
//        } else if (token.type == Token.TokenType.EndTag) {
//            Token.EndTag endTag = (Token.EndTag) token;
//            if (endTag.hasAttributes())
//                error("Attributes incorrectly present on end tag [/%s]", endTag.normalName());
//        }
    }

    public void emit(final String str) {
        // buffer strings up until last string token found, to emit only one token for a run of character refs etc.
        // does not set isEmitPending; read checks that
//        if (charsString == null) {
//            charsString = str;
//        } else {
//            if (charsBuilder.length() == 0) { // switching to string builder as more than one emit before read
//                charsBuilder.append(charsString);
//            }
//            charsBuilder.append(str);
//        }
//        charPending.startPos(charStartPos);
//        charPending.endPos(reader.pos());
    }

    void emit(final StringBuilder str) {

    }

    public void createBogusCommentPending() {
//        commentPending.reset();
//        commentPending.bogus = true;
    }

    public void transition(String key) {

    }

    public Token.Tag createTagPending(boolean b) {
        return new Tag() {
            @Override
            public Tag name(String appropriateEndTagName) {
                return super.name(appropriateEndTagName);
            }
        };
    }

    public int[] consumeCharacterReference(Character chr, boolean inAttribute) {
        return null;
    }

    public void eofError(CharHandler handler) {

    }

    public void emitTagPending() {

    }

    public void createTempBuffer() {
    }

    public String appropriateEndTagName() {
        return null;
    }
    String appropriateEndTagSeq() {
        return null;
    }

    public boolean isAppropriateEndTagToken() {
        return false;
    }

    public void emitCommentPending() {

    }

    public void createCommentPending() {

    }

    public void createDoctypePending() {

    }

    public void emitDoctypePending() {

    }
}
