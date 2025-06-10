package swingUI.constantes;

import javax.swing.*;
import java.awt.*;

public class MainPainel extends JFrame {

    protected String userRole;

    public MainPainel(String titulo) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(CoresUI.primary_color);
    }

    public MainPainel(String titulo, String role) {
        this(titulo);
        this.userRole = role;
    }

    protected int obterXCentralizado(int larguraComponente) {
        int larguraConteudo = getContentPane().getWidth();
        return (larguraConteudo - larguraComponente) / 2;
    }

    protected void posicionarComponenteCentralizado(JComponent componente, int y, int largura, int altura) {
        int x = obterXCentralizado(largura);
        componente.setBounds(x, y, largura, altura);
        getContentPane().add(componente);
    }

    protected void posicionarLabelCentralizado(JLabel label, int y, int largura, int altura) {
        int x = obterXCentralizado(largura);
        label.setBounds(x, y, largura, altura);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label);
    }
}
