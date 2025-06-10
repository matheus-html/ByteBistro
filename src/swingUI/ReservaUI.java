package swingUI;

import model.Reserva;
import service.ReservaDAO;
import service.ClienteDAO;
import service.MesaDAO;
import model.Cliente;
import model.Mesa;
import swingUI.constants.CoresUI;
import swingUI.constants.MainPainel;
import swingUI.garcom.GarcomUI;
import swingUI.gerente.GerenteUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ReservaUI extends MainPainel {

    private ReservaDAO reservaDAO;
    private ClienteDAO clienteDAO;
    private MesaDAO mesaDAO;

    private JTable tabelaClientes;
    private DefaultTableModel modeloTabelaClientes;
    private JTable tabelaMesas;
    private DefaultTableModel modeloTabelaMesas;
    private JTable tabelaReservas;
    private DefaultTableModel modeloTabelaReservas;

    private JTextField campoIdCliente;
    private JTextField campoIdMesa;
    private JFormattedTextField campoDataHora;
    private JTextField campoNumPessoas;

    private static final DateTimeFormatter FORMATADOR_DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReservaUI(String userRole) {
        super("Painel de Reservas - ByteBistro", userRole);
        reservaDAO = new ReservaDAO();
        clienteDAO = new ClienteDAO();
        mesaDAO = new MesaDAO();
        SwingUtilities.invokeLater(this::adicionarComponentesReserva);
    }

    private void adicionarComponentesReserva() {
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        painelSuperior.setBackground(CoresUI.primary_color);
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        String[] colunasClientes = {"ID Cliente", "Nome", "CPF", "Telefone"};
        modeloTabelaClientes = new DefaultTableModel(colunasClientes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClientes = new JTable(modeloTabelaClientes);
        configurarTabela(tabelaClientes);
        JScrollPane scrollClientes = new JScrollPane(tabelaClientes);
        scrollClientes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1), "Clientes", 0, 0, new Font("SansSerif", Font.BOLD, 14), CoresUI.accent_color));
        painelSuperior.add(scrollClientes);

        String[] colunasMesas = {"ID Mesa", "Capacidade", "Localização", "Status"};
        modeloTabelaMesas = new DefaultTableModel(colunasMesas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMesas = new JTable(modeloTabelaMesas);
        configurarTabela(tabelaMesas);
        tabelaMesas.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) value;

                if ("Ocupada".equals(status)) {
                    c.setBackground(CoresUI.danger_color.darker());
                    c.setForeground(CoresUI.text_color);
                } else if ("Disponivel".equals(status)) {
                    c.setBackground(CoresUI.success_color.darker());
                    c.setForeground(CoresUI.text_color);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }

                if (isSelected) {
                    c.setBackground(CoresUI.accent_color.brighter());
                    c.setForeground(CoresUI.primary_color);
                }
                return c;
            }
        });
        JScrollPane scrollMesas = new JScrollPane(tabelaMesas);
        scrollMesas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1), "Mesas", 0, 0, new Font("SansSerif", Font.BOLD, 14), CoresUI.accent_color));
        painelSuperior.add(scrollMesas);

        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelCentralInferior = new JPanel(new BorderLayout());
        painelCentralInferior.setBackground(CoresUI.primary_color);
        painelCentralInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        String[] colunasReservas = {"ID Reserva", "ID Cliente", "ID Mesa", "Data/Hora", "Pessoas"};
        modeloTabelaReservas = new DefaultTableModel(colunasReservas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaReservas = new JTable(modeloTabelaReservas);
        configurarTabela(tabelaReservas);
        JScrollPane scrollReservas = new JScrollPane(tabelaReservas);
        scrollReservas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1), "Reservas", 0, 0, new Font("SansSerif", Font.BOLD, 14), CoresUI.accent_color));
        painelCentralInferior.add(scrollReservas, BorderLayout.CENTER);

        add(painelCentralInferior, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(CoresUI.primary_color);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        Font fonteBotoes = new Font("SansSerif", Font.BOLD, 16);

        JButton btnAtualizarTabelas = new JButton("Atualizar Tudo");
        btnAtualizarTabelas.setBackground(CoresUI.secondary_color);
        btnAtualizarTabelas.setForeground(CoresUI.text_color);
        btnAtualizarTabelas.setFont(fonteBotoes);
        btnAtualizarTabelas.addActionListener(e -> {
            carregarClientesNaTabela();
            carregarMesasNaTabela();
            carregarReservasNaTabela();
            JOptionPane.showMessageDialog(this, "Tabelas atualizadas!");
        });
        painelBotoes.add(btnAtualizarTabelas);

        JButton btnAdicionar = new JButton("Adicionar Reserva");
        btnAdicionar.setBackground(CoresUI.success_color);
        btnAdicionar.setForeground(CoresUI.primary_color);
        btnAdicionar.setFont(fonteBotoes);
        btnAdicionar.addActionListener(e -> mostrarDialogoCadastroEdicao(null));
        painelBotoes.add(btnAdicionar);

        JButton btnEditar = new JButton("Editar Reserva");
        btnEditar.setBackground(CoresUI.accent_color);
        btnEditar.setForeground(CoresUI.primary_color);
        btnEditar.setFont(fonteBotoes);
        btnEditar.addActionListener(e -> editarReservaSelecionada());
        painelBotoes.add(btnEditar);

        JButton btnRemover = new JButton("Remover Reserva");
        btnRemover.setBackground(CoresUI.danger_color);
        btnRemover.setForeground(CoresUI.text_color);
        btnRemover.setFont(fonteBotoes);
        btnRemover.addActionListener(e -> removerReservaSelecionada());
        painelBotoes.add(btnRemover);

        JButton voltarBotao = new JButton("Voltar");
        voltarBotao.setBackground(CoresUI.secondary_color);
        voltarBotao.setForeground(CoresUI.text_color);
        voltarBotao.setFont(fonteBotoes);
        voltarBotao.addActionListener(e -> {
            if ("gerente".equals(userRole)) {
                new GerenteUI().setVisible(true);
            } else {
                new GarcomUI().setVisible(true);
            }
            dispose();
        });
        painelBotoes.add(voltarBotao);

        add(painelBotoes, BorderLayout.SOUTH);

        carregarClientesNaTabela();
        carregarMesasNaTabela();
        carregarReservasNaTabela();
    }

    private void configurarTabela(JTable table) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setSelectionBackground(CoresUI.accent_color.brighter());
        table.setSelectionForeground(CoresUI.primary_color);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(CoresUI.secondary_color);
        table.getTableHeader().setForeground(CoresUI.text_color);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void carregarClientesNaTabela() {
        modeloTabelaClientes.setRowCount(0);
        try {
            ArrayList<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente cliente : clientes) {
                Object[] rowData = {cliente.getId(), cliente.getNomeCliente(), cliente.getCpf(), cliente.getTelefone()};
                modeloTabelaClientes.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarMesasNaTabela() {
        modeloTabelaMesas.setRowCount(0);
        try {
            ArrayList<Mesa> mesas = mesaDAO.listarMesas();
            for (Mesa mesa : mesas) {
                Object[] rowData = {mesa.getId_mesa(), mesa.getCapacidade(), mesa.getLocalizacao(), mesa.getStatusMesa()};
                modeloTabelaMesas.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar mesas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarReservasNaTabela() {
        modeloTabelaReservas.setRowCount(0);
        try {
            ArrayList<Reserva> reservas = reservaDAO.listarReservas();
            for (Reserva reserva : reservas) {
                Object[] rowData = {reserva.getId_reserva(), reserva.getId_cliente(), reserva.getId_mesa(),
                        formatarTimestamp(reserva.getData_hora()), reserva.getNum_pessoas()};
                modeloTabelaReservas.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar reservas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatarTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        return timestamp.toLocalDateTime().format(FORMATADOR_DATA_HORA);
    }

    private void mostrarDialogoCadastroEdicao(Reserva reservaParaEditar) {
        campoIdCliente = new JTextField();
        campoIdMesa = new JTextField();
        try {
            MaskFormatter mask = new MaskFormatter("####-##-## ##:##:##");
            mask.setPlaceholderCharacter('_');
            campoDataHora = new JFormattedTextField(mask);
            campoDataHora.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        } catch (java.text.ParseException e) {
            campoDataHora = new JFormattedTextField();
            System.err.println("Erro ao criar máscara de data/hora: " + e.getMessage());
        }
        campoNumPessoas = new JTextField();

        if (reservaParaEditar != null) {
            campoIdCliente.setText(String.valueOf(reservaParaEditar.getId_cliente()));
            campoIdMesa.setText(String.valueOf(reservaParaEditar.getId_mesa()));
            campoDataHora.setText(formatarTimestamp(reservaParaEditar.getData_hora()));
            campoNumPessoas.setText(String.valueOf(reservaParaEditar.getNum_pessoas()));
        }

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 10, 10));
        painelDialogo.add(new JLabel("ID Cliente:"));
        painelDialogo.add(campoIdCliente);
        painelDialogo.add(new JLabel("ID Mesa:"));
        painelDialogo.add(campoIdMesa);
        painelDialogo.add(new JLabel("Data e Hora (AAAA-MM-DD HH:MM:SS):"));
        painelDialogo.add(campoDataHora);
        painelDialogo.add(new JLabel("Número de Pessoas:"));
        painelDialogo.add(campoNumPessoas);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo,
                reservaParaEditar == null ? "Cadastrar Nova Reserva" : "Editar Reserva",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int idCliente = Integer.parseInt(campoIdCliente.getText().trim());
                int idMesa = Integer.parseInt(campoIdMesa.getText().trim());

                String dataHoraStr = campoDataHora.getText().trim();

                if (dataHoraStr.contains("_")) {
                    throw new IllegalArgumentException("Por favor, preencha a data e hora completamente.");
                }

                LocalDateTime localDateTime = LocalDateTime.parse(dataHoraStr, FORMATADOR_DATA_HORA);
                Timestamp dataHoraReserva = Timestamp.valueOf(localDateTime);

                int numPessoas = Integer.parseInt(campoNumPessoas.getText().trim());

                if (reservaParaEditar == null) {
                    Reserva novaReserva = new Reserva(0, idCliente, idMesa, dataHoraReserva, numPessoas);
                    reservaDAO.cadastrarReserva(novaReserva);
                    JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!");
                } else {
                    reservaParaEditar.setId_cliente(idCliente);
                    reservaParaEditar.setId_mesa(idMesa);
                    reservaParaEditar.setData_hora(dataHoraReserva);
                    reservaParaEditar.setNum_pessoas(numPessoas);
                    reservaDAO.atualizarReserva(reservaParaEditar);
                    JOptionPane.showMessageDialog(this, "Reserva atualizada com sucesso!");
                }
                carregarReservasNaTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID do Cliente, ID da Mesa e Número de Pessoas devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException | DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarReservaSelecionada() {
        int linhaSelecionada = tabelaReservas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva na tabela para editar.", "Nenhuma Reserva Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) modeloTabelaReservas.getValueAt(linhaSelecionada, 0);
        try {
            int idCliente = (int) modeloTabelaReservas.getValueAt(linhaSelecionada, 1);
            int idMesa = (int) modeloTabelaReservas.getValueAt(linhaSelecionada, 2);
            String dataHoraStr = (String) modeloTabelaReservas.getValueAt(linhaSelecionada, 3);
            
            Timestamp dataHora = Timestamp.valueOf(LocalDateTime.parse(dataHoraStr, FORMATADOR_DATA_HORA));
            int numPessoas = (int) modeloTabelaReservas.getValueAt(linhaSelecionada, 4);

            Reserva reservaParaEditar = new Reserva(idReserva, idCliente, idMesa, dataHora, numPessoas);
            mostrarDialogoCadastroEdicao(reservaParaEditar);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao converter data/hora para edição: Verifique o formato na tabela. " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao preparar edição da reserva: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerReservaSelecionada() {
        int linhaSelecionada = tabelaReservas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma reserva na tabela para remover.", "Nenhuma Reserva Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) modeloTabelaReservas.getValueAt(linhaSelecionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover a reserva ID: " + idReserva + "?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                reservaDAO.removerReserva(idReserva);
                JOptionPane.showMessageDialog(this, "Reserva removida com sucesso!");
                carregarReservasNaTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover reserva: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}