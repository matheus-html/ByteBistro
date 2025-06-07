package controller;

import service.BancoConfig;
import swingUI.cardapio.CardapioUI;
import swingUI.cliente.ClienteUI;
import swingUI.comanda.ComandaUI;
import swingUI.garcom.GarcomUI;
import swingUI.gerente.GerenteUI;
import swingUI.login.LoginUI;
import swingUI.mesa.MesaUI;
import swingUI.reserva.ReservaUI;

import javax.swing.*;
import java.sql.SQLException;

public class ByteBistroController extends JFrame {
    public static void main(String[] args) throws SQLException {
        BancoConfig.criarTabelas();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
            new LoginUI().setVisible(true);
            }
        });
    }
}
