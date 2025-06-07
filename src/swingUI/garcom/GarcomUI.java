package swingUI.garcom;

import model.Cardapio;
import model.Cliente;
import model.Mesa;
import service.CardapioDAO;
import service.ClienteDAO;
import service.MesaDAO;
import swingUI.comanda.ComandaUI;
import swingUI.constants.CoresUI;
import swingUI.constants.MainPainel;
import swingUI.login.LoginUI;
import swingUI.reserva.ReservaUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GarcomUI extends MainPainel {

    private JTabbedPane abasPanel;

    private JTable tabelaClientes;
    private DefaultTableModel modeloTabelaClientes;
    private ClienteDAO clienteDAO;

    private JTable tabelaCardapio;
    private DefaultTableModel modeloTabelaCardapio;
    private CardapioDAO cardapioDAO;
    private JComboBox<String> comboCategoriasCardapio;

    private JTable tabelaMesas;
    private DefaultTableModel modeloTabelaMesas;
    private MesaDAO mesaDAO;

    public GarcomUI() {
        super("MainPainel Garçom - ByteBistro", "garcom");
        clienteDAO = new ClienteDAO();
        cardapioDAO = new CardapioDAO();
        mesaDAO = new MesaDAO();
        SwingUtilities.invokeLater(this::adicionarComponentesGarcom);
    }

    private void adicionarComponentesGarcom() {
        JLabel lblGarcom = new JLabel("PAINEL DO GARÇOM");
        lblGarcom.setForeground(CoresUI.accent_color);
        lblGarcom.setFont(new Font("SansSerif", Font.BOLD, 48));
        posicionarLabelCentralizado(lblGarcom, 30, getContentPane().getWidth(), 60);

        abasPanel = new JTabbedPane();
        abasPanel.setBounds(30, 100, getContentPane().getWidth() - 60, getContentPane().getHeight() - 200);
        abasPanel.setFont(new Font("SansSerif", Font.BOLD, 16));
        abasPanel.setBackground(CoresUI.secondary_color);
        abasPanel.setForeground(CoresUI.text_color);
        add(abasPanel);

        adicionarAbaClientes();
        adicionarAbaCardapio();
        adicionarAbaMesas();

        JButton btnGerenciarReservas = new JButton("Gerenciar Reservas");
        btnGerenciarReservas.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGerenciarReservas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarReservas.setForeground(CoresUI.text_color);
        btnGerenciarReservas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarReservas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarReservas.setBounds(30, getContentPane().getHeight() - 80, 250, 50);
        btnGerenciarReservas.addActionListener(e -> {
            new ReservaUI(this.userRole).setVisible(true); // Passando a role
            dispose();
        });
        add(btnGerenciarReservas);

        JButton btnGerenciarComandas = new JButton("Gerenciar Comandas");
        btnGerenciarComandas.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGerenciarComandas.setBackground(CoresUI.primary_color.brighter());
        btnGerenciarComandas.setForeground(CoresUI.text_color);
        btnGerenciarComandas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGerenciarComandas.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 2, true));
        btnGerenciarComandas.setBounds(300, getContentPane().getHeight() - 80, 250, 50);
        btnGerenciarComandas.addActionListener(e -> {
            new ComandaUI(this.userRole).setVisible(true); // Passando a role
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
            int op = JOptionPane.showConfirmDialog(null, "Deseja retornar para a tela de Login?", "Voltar para tela de Login", JOptionPane.YES_NO_OPTION);

            if(op == JOptionPane.YES_OPTION){
                new LoginUI().setVisible(true);
                dispose();
            }

        });
        add(btnVoltarLogin);
    }

    private void adicionarAbaClientes() {
        JPanel painelClientes = new JPanel();
        painelClientes.setLayout(new BorderLayout());
        painelClientes.setBackground(CoresUI.primary_color);

        modeloTabelaClientes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabelaClientes.addColumn("ID");
        modeloTabelaClientes.addColumn("Nome");
        modeloTabelaClientes.addColumn("CPF");
        modeloTabelaClientes.addColumn("Telefone");
        modeloTabelaClientes.addColumn("Email");

        tabelaClientes = new JTable(modeloTabelaClientes);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaClientes.setRowHeight(25);
        tabelaClientes.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaClientes.getTableHeader().setBackground(CoresUI.secondary_color);
        tabelaClientes.getTableHeader().setForeground(CoresUI.text_color);
        tabelaClientes.setBackground(CoresUI.text_color);
        tabelaClientes.setForeground(CoresUI.primary_color);

        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        painelClientes.add(scrollPane, BorderLayout.CENTER);

        carregarClientesNaTabela();
        abasPanel.addTab("Clientes", painelClientes);
    }

    private void carregarClientesNaTabela() {
        modeloTabelaClientes.setRowCount(0);
        ArrayList<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            modeloTabelaClientes.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNomeCliente(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEmail()
            });
        }
    }

    private void adicionarAbaCardapio() {
        JPanel painelCardapio = new JPanel();
        painelCardapio.setLayout(new BorderLayout());
        painelCardapio.setBackground(CoresUI.primary_color);

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.setBackground(CoresUI.primary_color);
        String[] categorias = {"Todos", "Entradas", "Pratos Principais", "Bebidas", "Sobremesas", "Pizzas", "Massas", "Salgados", "Doces"};
        comboCategoriasCardapio = new JComboBox<>(categorias);
        comboCategoriasCardapio.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboCategoriasCardapio.addActionListener(e -> carregarItensCardapioNaTabela());
        painelFiltro.add(new JLabel("Filtrar por Categoria:"));
        painelFiltro.add(comboCategoriasCardapio);
        painelCardapio.add(painelFiltro, BorderLayout.NORTH);

        modeloTabelaCardapio = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabelaCardapio.addColumn("ID");
        modeloTabelaCardapio.addColumn("Nome");
        modeloTabelaCardapio.addColumn("Descrição");
        modeloTabelaCardapio.addColumn("Preço");
        modeloTabelaCardapio.addColumn("Categoria");

        tabelaCardapio = new JTable(modeloTabelaCardapio);
        tabelaCardapio.getTableHeader().setReorderingAllowed(false);
        tabelaCardapio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCardapio.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaCardapio.setRowHeight(25);
        tabelaCardapio.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaCardapio.getTableHeader().setBackground(CoresUI.secondary_color);
        tabelaCardapio.getTableHeader().setForeground(CoresUI.text_color);
        tabelaCardapio.setBackground(CoresUI.text_color);
        tabelaCardapio.setForeground(CoresUI.primary_color);

        JScrollPane scrollPane = new JScrollPane(tabelaCardapio);
        painelCardapio.add(scrollPane, BorderLayout.CENTER);

        carregarItensCardapioNaTabela();
        abasPanel.addTab("Cardápio", painelCardapio);
    }

    private void carregarItensCardapioNaTabela() {
        modeloTabelaCardapio.setRowCount(0);
        ArrayList<Cardapio> itens;
        String categoriaSelecionada = (String) comboCategoriasCardapio.getSelectedItem();

        if ("Todos".equals(categoriaSelecionada)) {
            itens = cardapioDAO.listarItens();
        } else {
            itens = cardapioDAO.listarItensPorCategoria(categoriaSelecionada);
        }

        for (Cardapio item : itens) {
            modeloTabelaCardapio.addRow(new Object[]{
                    item.getId_item(),
                    item.getNome_item(),
                    item.getDescricao(),
                    item.getPreco(),
                    item.getCategoria()
            });
        }
    }

    private void adicionarAbaMesas() {
        JPanel painelMesas = new JPanel();
        painelMesas.setLayout(new BorderLayout());
        painelMesas.setBackground(CoresUI.primary_color);

        modeloTabelaMesas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabelaMesas.addColumn("ID");
        modeloTabelaMesas.addColumn("Capacidade");
        modeloTabelaMesas.addColumn("Localização");
        modeloTabelaMesas.addColumn("Status");

        tabelaMesas = new JTable(modeloTabelaMesas);
        tabelaMesas.getTableHeader().setReorderingAllowed(false);
        tabelaMesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMesas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaMesas.setRowHeight(25);
        tabelaMesas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaMesas.getTableHeader().setBackground(CoresUI.secondary_color);
        tabelaMesas.getTableHeader().setForeground(CoresUI.text_color);
        tabelaMesas.setBackground(CoresUI.text_color);
        tabelaMesas.setForeground(CoresUI.primary_color);

        JScrollPane scrollPane = new JScrollPane(tabelaMesas);
        painelMesas.add(scrollPane, BorderLayout.CENTER);

        carregarMesasNaTabela();
        abasPanel.addTab("Mesas", painelMesas);
    }

    private void carregarMesasNaTabela() {
        modeloTabelaMesas.setRowCount(0);
        ArrayList<Mesa> mesas = mesaDAO.listarMesas();
        for (Mesa mesa : mesas) {
            modeloTabelaMesas.addRow(new Object[]{
                    mesa.getId_mesa(),
                    mesa.getCapacidade(),
                    mesa.getLocalizacao(),
                    mesa.getStatusMesa()
            });
        }
    }
}