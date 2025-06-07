package swingUI.login;

import service.LoginDAO;
import swingUI.constants.CoresUI;
import swingUI.garcom.GarcomUI;
import swingUI.gerente.GerenteUI;
import swingUI.constants.MainPainel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends MainPainel {
    public LoginUI() {
        super("MainPainel login - ByteBistro");
        SwingUtilities.invokeLater(() -> addComponentesLogin());
    }

    private void addComponentesLogin(){
        int larguraConteudo = 500;
        int larguraTotalDaTela = getContentPane().getWidth();

        JLabel loginLabel = new JLabel("BEM-VINDO AO BYTEBISTRO");
        loginLabel.setForeground(CoresUI.accent_color);
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 56));
        posicionarLabelCentralizado(loginLabel, 150, larguraTotalDaTela, 100);

        JLabel rotuloSubtitulo = new JLabel("Acesse sua conta de gerenciamento");
        rotuloSubtitulo.setForeground(CoresUI.muted_text_color);
        rotuloSubtitulo.setFont(new Font("SansSerif", Font.ITALIC, 22));
        posicionarLabelCentralizado(rotuloSubtitulo, 240, larguraTotalDaTela, 50);

        JLabel nomeUsuarioLabel = new JLabel("Nome de Usuário:");
        nomeUsuarioLabel.setForeground(CoresUI.text_color);
        nomeUsuarioLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        posicionarComponenteCentralizado(nomeUsuarioLabel, 350, larguraConteudo, 30);

        JTextField nomeUsuarioText = new JTextField();
        nomeUsuarioText.setForeground(CoresUI.text_color);
        nomeUsuarioText.setFont(new Font("Dialog", Font.PLAIN, 28));
        nomeUsuarioText.setBackground(CoresUI.primary_color.darker());
        nomeUsuarioText.setCaretColor(CoresUI.accent_color);
        posicionarComponenteCentralizado(nomeUsuarioText, 385, larguraConteudo, 60);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setForeground(CoresUI.text_color);
        senhaLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        posicionarComponenteCentralizado(senhaLabel, 485, larguraConteudo, 30);

        JPasswordField senhaField = new JPasswordField();
        senhaField.setForeground(CoresUI.text_color);
        senhaField.setFont(new Font("Dialog", Font.PLAIN, 28));
        senhaField.setBackground(CoresUI.primary_color.darker());
        senhaField.setCaretColor(CoresUI.accent_color);
        posicionarComponenteCentralizado(senhaField, 520, larguraConteudo, 60);

        JButton loginBotao = new JButton("Login");
        int larguraBotao = 250;
        loginBotao.setFont(new Font("Dialog", Font.BOLD, 24));
        loginBotao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBotao.setBackground(CoresUI.accent_color);
        loginBotao.setForeground(CoresUI.primary_color);
        loginBotao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginBotao.setFocusPainted(false);
        posicionarComponenteCentralizado(loginBotao, 650, larguraBotao, 60);

        loginBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String nomeUsuario = nomeUsuarioText.getText();
                String senha = new String(senhaField.getPassword());
                String role = LoginDAO.ValidarLogin(nomeUsuario, senha);

                if(role != null){
                    if("gerente".equals(role)){
                        new GerenteUI().setVisible(true);
                    } else {
                        new GarcomUI().setVisible(true);
                    }
                    LoginUI.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "LoginDAO falhou. Verifique seu nome de usuário e senha.", "Erro de LoginDAO", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
