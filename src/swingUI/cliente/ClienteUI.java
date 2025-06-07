package swingUI.cliente;

import model.Cliente;
import service.ClienteDAO;
import swingUI.constants.CoresUI;
import swingUI.constants.MainPainel;
import swingUI.gerente.GerenteUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteUI extends MainPainel {

    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private ClienteDAO clienteDAO;

    public ClienteUI() {
        super("MainPainel Clientes - ByteBistro");
        clienteDAO = new ClienteDAO();
        SwingUtilities.invokeLater(this::addComponentesCliente);
    }

    public void addComponentesCliente(){

        int larguraTotalDaTela = getContentPane().getWidth();

        JLabel tituloLabel = new JLabel("GERENCIAMENTO DE CLIENTES");
        tituloLabel.setForeground(CoresUI.accent_color);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 56));
        posicionarLabelCentralizado(tituloLabel, 50, larguraTotalDaTela, 50);

        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Telefone");
        modeloTabela.addColumn("Email");

        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBounds(30, 150, larguraTotalDaTela - 60, 450);
        add(scrollPane);

        Font fonteBotoes = new Font("SansSerif", Font.BOLD, 16);

        JButton btnRefresh = new JButton("Atualizar Tabela");
        btnRefresh.setBounds(30, 620, 180, 50);
        btnRefresh.setBackground(CoresUI.warning_color);
        btnRefresh.setForeground(CoresUI.primary_color);
        btnRefresh.setFont(fonteBotoes);
        btnRefresh.addActionListener(e -> carregarClientesNaTabela());
        add(btnRefresh);

        JButton btnAdicionar = new JButton("Adicionar Cliente");
        btnAdicionar.setBounds(220, 620, 180, 50);
        btnAdicionar.setBackground(CoresUI.success_color);
        btnAdicionar.setForeground(CoresUI.text_color);
        btnAdicionar.setFont(fonteBotoes);
        btnAdicionar.addActionListener(e -> adicionarCliente());
        add(btnAdicionar);

        JButton btnEditar = new JButton("Editar Cliente");
        btnEditar.setBounds(410, 620, 180, 50);
        btnEditar.setBackground(CoresUI.accent_color);
        btnEditar.setForeground(CoresUI.primary_color);
        btnEditar.setFont(fonteBotoes);
        btnEditar.addActionListener(e -> editarCliente());
        add(btnEditar);

        JButton btnRemover = new JButton("Remover Cliente");
        btnRemover.setBounds(600, 620, 180, 50);
        btnRemover.setBackground(CoresUI.danger_color);
        btnRemover.setForeground(CoresUI.text_color);
        btnRemover.setFont(fonteBotoes);
        btnRemover.addActionListener(e -> removerCliente());
        add(btnRemover);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(800, 620, 150, 50);
        btnVoltar.setBackground(CoresUI.secondary_color);
        btnVoltar.setForeground(CoresUI.text_color);
        btnVoltar.setFont(fonteBotoes);
        btnVoltar.addActionListener(e -> {
            GerenteUI gerenteUI = new GerenteUI();
            gerenteUI.setVisible(true);
            dispose();
        });
        add(btnVoltar);

        carregarClientesNaTabela();
    }

    private void carregarClientesNaTabela(){
        modeloTabela.setRowCount(0);
        ArrayList<Cliente> clientes = clienteDAO.listarClientes();

        for (Cliente cliente : clientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNomeCliente(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEmail()
            });
        }
    }

    private void adicionarCliente() {
        JTextField campoNomeCliente = new JTextField();
        JTextField campoCPF = new JTextField();
        JTextField campoTelefone = new JTextField();
        JTextField campoEmail = new JTextField();

        Object[] campos = {
                "Nome:", campoNomeCliente,
                "CPF:", campoCPF,
                "Telefone:", campoTelefone,
                "Email:", campoEmail
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Adicionar Novo Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {

            String nomeCliente = campoNomeCliente.getText().trim();
            String cpf = campoCPF.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String email = campoEmail.getText().trim();

            if (validadores(campoNomeCliente, campoCPF, campoTelefone, campoEmail, cpf, telefone, email)) return;

            String cpfFormatado = formatarCpf(cpf);
            String telefoneFormatado = formatarTelefone(telefone);

            Cliente novoCliente = new Cliente(nomeCliente, cpfFormatado, telefoneFormatado, email);
            try {
                clienteDAO.inserirCliente(novoCliente);
                carregarClientesNaTabela();
                JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String formatarCpf(String cpf) {
        String apenasDigitos = cpf.replaceAll("[^0-9]", "");
        if (apenasDigitos.length() == 11) {
            return apenasDigitos.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        return cpf;
    }

    private String formatarTelefone(String telefone) {
        String apenasDigitos = telefone.replaceAll("[^0-9]", "");
        if (apenasDigitos.length() == 11) {
            return apenasDigitos.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (apenasDigitos.length() == 10) {
            return apenasDigitos.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
        return telefone;
    }

    private boolean validadores(JTextField campoNomeCliente, JTextField campoCPF, JTextField campoTelefone, JTextField campoEmail, String cpf, String telefone, String email) {
        if(campoNomeCliente.getText().trim().isEmpty() || campoCPF.getText().trim().isEmpty() || campoTelefone.getText().trim().isEmpty() ||
                campoEmail.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!");
            return true;
        }

        if (!cpf.matches("(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})|(\\d{11})")) {
            JOptionPane.showMessageDialog(this, "CPF inválido. Use o formato DDD.DDD.DDD-DD ou apenas 11 dígitos numéricos.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return true;
        }

        if (!telefone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Telefone inválido. Insira apenas 10 ou 11 dígitos (incluindo o DDD).", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return true;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "E-mail inválido. Verifique o formato.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private void editarCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String cpfAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        String telefoneAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
        String emailAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 4);

        JTextField campoNomeCliente = new JTextField(nomeAtual);
        JTextField campoCPF = new JTextField(cpfAtual);
        JTextField campoTelefone = new JTextField(telefoneAtual);
        JTextField campoEmail = new JTextField(emailAtual);

        Object[] campos = {
                "Nome:", campoNomeCliente,
                "CPF:", campoCPF,
                "Telefone:", campoTelefone,
                "Email:", campoEmail
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {

            String nomeCliente = campoNomeCliente.getText().trim();
            String cpf = campoCPF.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String email = campoEmail.getText().trim();

            if (campoNomeCliente.getText().trim().isEmpty() ||
                    campoCPF.getText().trim().isEmpty() ||
                    campoTelefone.getText().trim().isEmpty() ||
                    campoEmail.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (validadores(campoNomeCliente, campoCPF, campoTelefone, campoEmail, cpf, telefone, email)) return;

            String cpfFormatado = formatarCpf(cpf);
            String telefoneFormatado = formatarTelefone(telefone);

            Cliente clienteEditado = new Cliente(id, nomeCliente, cpfFormatado, telefoneFormatado, email);
            try {
                clienteDAO.atualizarCliente(clienteEditado);
                carregarClientesNaTabela();
                JOptionPane.showMessageDialog(this, "Cliente editado com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao editar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para remover.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o cliente selecionado?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            clienteDAO.removerCliente(id);
            carregarClientesNaTabela();
            JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
        }
    }
}