package constantes;

import javax.swing.*;

public class Painel extends JFrame {
    public Painel(String titulo) {
        super(titulo);
        setSize(900, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(CoresUI.primary_color);
    }
}
