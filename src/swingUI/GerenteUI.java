package swingUI;

import constantes.CoresUI;
import constantes.Painel;

import javax.swing.*;
import java.awt.*;

public class GerenteUI extends Painel {
    public GerenteUI() {
        super("Gerente");
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
    }
}
