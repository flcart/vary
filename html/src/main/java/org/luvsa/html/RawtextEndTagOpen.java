package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:14
 */
public class RawtextEndTagOpen implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.readEndTag(t, r, "RawtextEndTagName", "Rawtext");
    }
}
