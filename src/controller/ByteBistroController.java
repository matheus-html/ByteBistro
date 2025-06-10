package controller;

import service.BancoConfig;
import swingUI.ReservaUI;

import javax.swing.*;
import java.sql.SQLException;

public class ByteBistroController extends JFrame {
    public static void main(String[] args) throws SQLException {
        BancoConfig.criarTabelas();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
            new ReservaUI("admin").setVisible(true);
            }
        });
    }
}
