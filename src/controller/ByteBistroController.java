package controller;

import constantes.BancoConfig;
import constantes.Cliente;
import service.ClienteDAO;
import swingUI.LoginUI;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ByteBistroController extends JFrame {
    private JTextField textField1;

    public static void main(String[] args) throws SQLException {
        BancoConfig.criarBanco();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new LoginUI().setVisible(true);
            }
        });

//        ClienteDAO clienteDAO = new ClienteDAO();
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("\nDigite o ID do cliente que deseja editar:");
//        int id = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("\nLista de clientes:");
//        clienteDAO.listarClientes();
//
//        System.out.println("\nDigite o nome do cliente:");
//        String nome = scanner.nextLine();
//
//        System.out.println("Digite o novo CPF:");
//        String cpf = scanner.nextLine();
//
//        System.out.println("Digite o novo telefone:");
//        String telefone = scanner.nextLine();
//
//        System.out.println("Digite o novo email:");
//        String email = scanner.nextLine();
//
//        Cliente clienteEditado = new Cliente(id, nome, cpf, telefone, email);
//
//        clienteDAO.editarCliente(clienteEditado);
//
//        System.out.println("\nClientes após a edição:");
//        clienteDAO.listarClientes();
    }
}
