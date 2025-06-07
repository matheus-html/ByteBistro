package swingUI.gerente;

import swingUI.comanda.ComandaUI;
import swingUI.mesa.MesaUI;
import swingUI.cliente.ClienteUI;
import swingUI.constants.CoresUI;
import swingUI.constants.MainPainel;
import swingUI.login.LoginUI;
import swingUI.reserva.ReservaUI;

import javax.swing.*;
import java.awt.*;

public class GerenteUI extends MainPainel {
    public GerenteUI() {
        super("MainPainel Gerente - ByteBistro", "gerente");
        SwingUtilities.invokeLater(() -> adicionarComponentesGerente());
    }

    private void adicionarComponentesGerente(){
        JLabel lblGerente = new JLabel("PAINEL DO GERENTE");
        lblGerente.setForeground(CoresUI.accent_color);
        lblGerente.setFont(new Font("SansSerif", Font.BOLD, 48));
        posicionarLabelCentralizado(lblGerente, 50, getContentPane().getWidth(), 60);

        int larguraBotao = 300;
        int alturaBotao = 60;
        int espacoEntreBotoes = 20;
        int yInicial = 180;

        JButton btnGerenciarUsuarios = new JButton("Gerenciar Usuários");
        btnGerenciarUsuarios.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnGerenciarUsuarios.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarUsuarios.setForeground(CoresUI.text_color);
        btnGerenciarUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarUsuarios.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarUsuarios.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Gerenciar Usuários clicado!");
        });
        posicionarComponenteCentralizado(btnGerenciarUsuarios, yInicial, larguraBotao, alturaBotao);

        JButton btnGerenciarClientes = new JButton("Gerenciar Clientes");
        btnGerenciarClientes.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnGerenciarClientes.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarClientes.setForeground(CoresUI.text_color);
        btnGerenciarClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarClientes.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarClientes.addActionListener(e -> {
            new ClienteUI().setVisible(true);
            dispose();
        });
        posicionarComponenteCentralizado(btnGerenciarClientes, yInicial + (alturaBotao + espacoEntreBotoes), larguraBotao, alturaBotao);

        JButton btnGerenciarMesas = new JButton("Gerenciar Mesas");
        btnGerenciarMesas.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnGerenciarMesas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarMesas.setForeground(CoresUI.text_color);
        btnGerenciarMesas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarMesas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarMesas.addActionListener(e -> {
            new MesaUI().setVisible(true);
            dispose();
        });
        posicionarComponenteCentralizado(btnGerenciarMesas, yInicial + 2 * (alturaBotao + espacoEntreBotoes), larguraBotao, alturaBotao);

        JButton btnGerenciarReservas = new JButton("Gerenciar Reservas");
        btnGerenciarReservas.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnGerenciarReservas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarReservas.setForeground(CoresUI.text_color);
        btnGerenciarReservas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarReservas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarReservas.addActionListener(e -> {
            new ReservaUI(this.userRole).setVisible(true); // Passando a role
            dispose();
        });
        posicionarComponenteCentralizado(btnGerenciarReservas, yInicial + 3 * (alturaBotao + espacoEntreBotoes), larguraBotao, alturaBotao);

        JButton btnGerenciarCardapio = new JButton("Gerenciar Cardápio");
        btnGerenciarCardapio.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnGerenciarCardapio.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarCardapio.setForeground(CoresUI.text_color);
        btnGerenciarCardapio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarCardapio.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarCardapio.addActionListener(e -> {

        });
        posicionarComponenteCentralizado(btnGerenciarCardapio, yInicial + 4 * (alturaBotao + espacoEntreBotoes), larguraBotao, alturaBotao);

        JButton btnGerenciarComandas = new JButton("Gerenciar Comandas");
        btnGerenciarComandas.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnGerenciarComandas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarComandas.setForeground(CoresUI.text_color);
        btnGerenciarComandas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarComandas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarComandas.addActionListener(e -> {
            new ComandaUI(this.userRole).setVisible(true); // Passando a role
            dispose();
        });
        posicionarComponenteCentralizado(btnGerenciarComandas, yInicial + 5 * (alturaBotao + espacoEntreBotoes), larguraBotao, alturaBotao);

        JButton btnVoltarLogin = new JButton("Voltar para LoginDAO");
        btnVoltarLogin.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnVoltarLogin.setBackground(CoresUI.muted_text_color);
        btnVoltarLogin.setForeground(CoresUI.primary_color);
        btnVoltarLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltarLogin.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(null, "Deseja retornar para a tela de LoginDAO?", "Voltar para tela de LoginDAO", JOptionPane.YES_NO_OPTION);

            if(op == JOptionPane.YES_OPTION){
                new LoginUI().setVisible(true);
                dispose();
            }

        });
        posicionarComponenteCentralizado(btnVoltarLogin, yInicial + 6 * (alturaBotao + espacoEntreBotoes) + 20, larguraBotao, alturaBotao);
    }
}
