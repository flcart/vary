package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:40
 */
public class ScriptDataEscapedEndTagName implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.handleDataEndTag(t, r, "ScriptDataEscaped");
    }

}
