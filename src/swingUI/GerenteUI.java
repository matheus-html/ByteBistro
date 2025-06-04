package swingUI;

import constantes.CoresUI;
import constantes.Painel;

import javax.swing.*;
import java.awt.*;

public class GerenteUI extends Painel {
    public GerenteUI() {
        super("Painel gerente - ByteBistro");
        JLabel welcomeLabel = new JLabel("Bem vindo(a), Gerente!");
        welcomeLabel.setBounds(50, 50, 400, 30);
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        welcomeLabel.setForeground(CoresUI.text_color);
        add(welcomeLabel);

        JButton registrarUsuarioBotao = new JButton("Registrar Novo Usuario");
        registrarUsuarioBotao.setBounds(125, 150, 250, 50);
        registrarUsuarioBotao.addActionListener(e ->{
                new RegistroUI().setVisible(true);}
        );
        add(registrarUsuarioBotao);

        JButton gerenciarClientesBotao = new JButton("Gerenciar Clientes");
        gerenciarClientesBotao.setBounds(125, 220, 250, 50);
        gerenciarClientesBotao.addActionListener(e ->{
            new ClienteUI().setVisible(true);
        });
        add(gerenciarClientesBotao);

        JButton voltarBotao = new JButton("Voltar para tela de Login");
        voltarBotao.setBounds(125, 270, 250, 50);
        voltarBotao.addActionListener(e ->{
            int input = JOptionPane.showConfirmDialog(GerenteUI.this, "Tem certeza que deseja voltar para a tela de login?");
            if (input == JOptionPane.YES_OPTION) {
                new LoginUI().setVisible(true);
            }
        });
        add(voltarBotao);
    }
}
