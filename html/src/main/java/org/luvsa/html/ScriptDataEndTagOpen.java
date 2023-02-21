package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:24
 */
public class ScriptDataEndTagOpen implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.readEndTag(t, r, "ScriptDataEndTagName", "ScriptData");
    }
}
