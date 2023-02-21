package org.luvsa.html;

/**
 * @author Aglet
 * @create 2023/2/17 15:02
 */
public class CharacterReferenceInData implements CharHandler {
    @Override
    public void read(Tokenizer t, CharReader reader) {
        CharHandler.readCharRef(t, "");
    }

}
