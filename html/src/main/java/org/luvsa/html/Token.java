package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 14:17
 */
public abstract class Token {


     final static class EOF extends Token {

//        @Override
//        Token reset() {
//            super.reset();
//            return this;
//        }

        @Override
        public String toString() {
            return "";
        }
    }

    final static class Comment extends Token {

        final Comment append(char append) {
            return this;
        }

        final Comment append(String append) {
            return this;
        }

    }

    static abstract class Tag extends Token {
        public boolean selfClosing;

        final void appendTagName(String append) {
            // might have null chars - need to replace with null replacement character
//            append = append.replace(TokeniserState.nullChar, Tokeniser.replacementChar);
//            tagName = tagName == null ? append : tagName.concat(append);
//            normalName = ParseSettings.normalName(tagName);
        }

        final void appendTagName(char append) {
            appendTagName(String.valueOf(append));
        }

        public Tag name(String appropriateEndTagName) {
//            tagName = name;
//            normalName = ParseSettings.normalName(tagName);
            return this;
        }

        final void appendAttributeName(String append) {

        }
        public void appendAttributeName(char c) {

        }

        public void newAttribute() {
        }

        public void appendAttributeValue(char replacementChar) {

        }

        public void appendAttributeValue(String replacementChar) {

        }

        public void appendAttributeValue(int[] replacementChar) {

        }

        public void setEmptyAttributeValue() {

        }
    }

    final static class StartTag extends Tag {

    }

    static final class Doctype extends Token {
        boolean forceQuirks = false;

        final StringBuilder name = new StringBuilder();
    }
}
