package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:45
 */
public class ScriptDataDoubleEscapeEnd implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.handleDataDoubleEscapeTag(t,r, "ScriptDataEscaped", "ScriptDataDoubleEscaped");
    }
}
