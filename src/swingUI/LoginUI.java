package swingUI;

import constantes.CoresUI;
import constantes.Painel;
import service.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends Painel {
    public LoginUI() {
        super("Login");
        addComponentesLogin();
    }

    private void addComponentesLogin(){
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(0,25, 520, 100);
        loginLabel.setForeground(CoresUI.text_color);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);

        //NOME DE USUARIO:
        JLabel nomeUsuarioLabel = new JLabel("Nome de Usuário:");
        nomeUsuarioLabel.setBounds(30, 150, 400, 25);
        nomeUsuarioLabel.setForeground(CoresUI.text_color);
        nomeUsuarioLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(nomeUsuarioLabel);

        //USUARIO TEXTINHO
        JTextField nomeUsuarioText = new JTextField();
        nomeUsuarioText.setBounds(30, 185, 450, 55);
        nomeUsuarioText.setForeground(CoresUI.secondary_color);
        nomeUsuarioText.setForeground(CoresUI.text_color);
        nomeUsuarioText.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(nomeUsuarioText);

        //SENHAAAAAA:
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(30, 335, 400, 25);
        senhaLabel.setForeground(CoresUI.text_color);
        senhaLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(senhaLabel);

        JPasswordField senhaField = new JPasswordField();
        senhaField.setBounds(30, 365, 450, 55);
        senhaField.setForeground(CoresUI.secondary_color);
        senhaField.setForeground(CoresUI.text_color);
        senhaField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(senhaField);

        JButton loginBotao = new JButton("Login");
        loginBotao.setFont(new Font("Dialog", Font.BOLD, 18));

        loginBotao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBotao.setBackground(CoresUI.text_color);
        loginBotao.setBounds(125,520,250,50);

        loginBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String nomeUsuario = nomeUsuarioText.getText();
                String senha = new String(senhaField.getPassword());
                String role = Login.ValidarLogin(nomeUsuario, senha);

                if(role != null){
                    if("gerente".equals(role)){
                        new GerenteUI().setVisible(true);
                    } else {
                        new GarcomUI().setVisible(true);
                    }
                    LoginUI.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Login falhou...");
                }
            }
        });
        add(loginBotao);
    }
}
