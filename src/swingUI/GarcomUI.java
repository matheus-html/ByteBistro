package swingUI;

import swingUI.constantes.CoresUI;
import swingUI.constantes.MainPainel;

import javax.swing.*;
import java.awt.*;

public class GarcomUI extends MainPainel {

    public GarcomUI() {
        super("MainPainel Garçom - ByteBistro", "garcom");
        SwingUtilities.invokeLater(this::adicionarComponentesGarcom);
    }

    private void adicionarComponentesGarcom() {
        JLabel lblGarcom = new JLabel("PAINEL DO GARÇOM");
        lblGarcom.setForeground(CoresUI.accent_color);
        lblGarcom.setFont(new Font("SansSerif", Font.BOLD, 48));
        posicionarLabelCentralizado(lblGarcom, 30, getContentPane().getWidth(), 60);

        int larguraBotao = 250;
        int alturaBotao = 50;
        int espacamentoEntreBotoes = 30;
        int larguraTotalBotoes = (larguraBotao * 2) + espacamentoEntreBotoes;
        int startX = (getContentPane().getWidth() - larguraTotalBotoes) / 2;
        int startY = (getContentPane().getHeight() - alturaBotao) / 2;

        JButton btnGerenciarReservas = new JButton("Gerenciar Reservas");
        btnGerenciarReservas.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGerenciarReservas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarReservas.setForeground(CoresUI.text_color);
        btnGerenciarReservas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarReservas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarReservas.setBounds(startX, startY, larguraBotao, alturaBotao);
        btnGerenciarReservas.addActionListener(e -> {
            new ReservaUI(this.userRole).setVisible(true);
            dispose();
        });
        add(btnGerenciarReservas);

        JButton btnGerenciarComandas = new JButton("Gerenciar Comandas");
        btnGerenciarComandas.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGerenciarComandas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarComandas.setForeground(CoresUI.text_color);
        btnGerenciarComandas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarComandas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarComandas.setBounds(startX + larguraBotao + espacamentoEntreBotoes,
                startY, larguraBotao, alturaBotao);
        btnGerenciarComandas.addActionListener(e -> {
            new ComandaUI(this.userRole).setVisible(true);
            dispose();
        });
        add(btnGerenciarComandas);

        JButton btnVoltarLogin = new JButton("Voltar para Login");
        btnVoltarLogin.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnVoltarLogin.setBackground(CoresUI.muted_text_color);
        btnVoltarLogin.setForeground(CoresUI.primary_color);
        btnVoltarLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVoltarLogin.setBounds(getContentPane().getWidth() - 280, getContentPane().getHeight() - 80, 250, 50);
        btnVoltarLogin.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(null, "Deseja retornar para a tela de Login?",
                    "Voltar para tela de Login",
                    JOptionPane.YES_NO_OPTION);

            if(op == JOptionPane.YES_OPTION){
                new LoginUI().setVisible(true);
                dispose();
            }

        });
        add(btnVoltarLogin);
    }
}