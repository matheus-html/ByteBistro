package controller;

import constantes.BancoConfig;
import constantes.Reserva;
import service.ReservaDAO;
import swingUI.LoginUI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.sql.SQLException;

public class ByteBistroController extends JFrame {
    public static void main(String[] args) throws SQLException {
        BancoConfig.criarTabelas();
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run(){
//               new LoginUI().setVisible(true);
//            }
//        });

        String dataHoraString = "2025-06-05 19:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dataHoraString, formatter);
        Timestamp dataHoraReserva = Timestamp.valueOf(localDateTime);
        Reserva novaReserva = new Reserva(1, 1, dataHoraReserva, 2);
        ReservaDAO reservaDAO = new ReservaDAO();
        reservaDAO.cadastrarReserva(novaReserva);

    }
}
