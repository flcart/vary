package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/18 9:41
 */
public class ScriptDataDoubleEscapeStart implements CharHandler{
    @Override
    public void read(Tokenizer t, CharReader r) {
        CharHandler.handleDataDoubleEscapeTag(t, r, "ScriptDataDoubleEscaped", "ScriptDataEscaped");
    }


}
