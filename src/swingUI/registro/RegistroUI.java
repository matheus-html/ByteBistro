package swingUI.registro;

import swingUI.constants.MainPainel;

import javax.swing.*;

public class RegistroUI extends MainPainel {
    public RegistroUI() {
        super("Registro");
        addComponentesRegistro();
    }

    private void addComponentesRegistro(){
        JLabel registroLabel = new JLabel("Registro");

        add(registroLabel);
    }


}
