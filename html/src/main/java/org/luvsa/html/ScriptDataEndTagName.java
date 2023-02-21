package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:25
 */
public class ScriptDataEndTagName implements CharHandler {

    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.handleDataEndTag(t, r, "ScriptData");
    }

}
