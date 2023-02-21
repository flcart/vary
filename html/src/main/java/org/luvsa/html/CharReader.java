package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 14:22
 */
public class CharReader {
    static final char EOF = (char) -1;

    public char current() {
        return 0;
    }

    public char consume() {
        return 0;
    }

    public String consumeData() {
        return null;
    }

    public boolean matchesAsciiAlpha() {
        return false;
    }

    public void advance() {

    }

    public String consumeRawData() {
        return null;
    }



    public String consumeTo(char c) {
       return null;
    }


    public boolean isEmpty() {
        return false;
    }

    public boolean matches(char c) {
        return false;
    }

    public String consumeTagName() {
        return null;
    }

    public void unconsume() {

    }

    boolean containsIgnoreCase(String seq) {
        return false;
    }

    public String consumeLetterSequence() {
        return null;
    }

    public boolean matchesLetter() {
        return false;
    }

    public String consumeToAny(char...chars) {
        return null;
    }

    String consumeToAnySorted(final char... chars) {
        return null;
    }

    public String consumeAttributeQuoted(boolean b) {
        return null;
    }

    public boolean matchConsume(String s) {
        return false;
    }

    public boolean matchConsumeIgnoreCase(String doctype) {
        return false;
    }

    public boolean matchesAny(char...chars) {
        return false;
    }
}
