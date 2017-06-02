package gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created by martin on 02/06/17.
 */
public class TxtFieldFormatter extends PlainDocument{

    private static final long serialVersionUID = 1L;

    @Override
    public void insertString(int off, String str, AttributeSet as) throws BadLocationException {
        // Prüfen, ob bereits 5 Zeichen drin stehen
        if(getLength() >= 3) return;

        // Prüfen, ob die einzufügenden Zeichen Zahlen sind
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))) return;
        }

        // Zahl(en) einfügen
        super.insertString(off, str, as);
    }
}
