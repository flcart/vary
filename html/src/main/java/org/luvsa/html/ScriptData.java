package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 15:47
 */
public class ScriptData implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.readRawData(t, r, this, "ScriptDataLessthanSign");
    }

}
