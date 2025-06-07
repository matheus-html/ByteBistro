package swingUI.mesa;

import model.Mesa;
import service.MesaDAO;
import swingUI.constants.CoresUI;
import swingUI.constants.MainPainel;
import swingUI.gerente.GerenteUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;

public class MesaUI extends MainPainel {

    private MesaDAO mesaDAO;
    private JTable tabelaMesas;
    private DefaultTableModel modeloTabelaMesas;
    private JScrollPane scrollPaneTabela;

    public MesaUI() {
        super("MainPainel Mesas - ByteBistro");
        mesaDAO = new MesaDAO();
        SwingUtilities.invokeLater(this::adicionarComponentesMesa);
    }

    private void adicionarComponentesMesa() {
        int larguraTotalDaTela = getContentPane().getWidth();
        int alturaTotalDaTela = getContentPane().getHeight();

        JLabel tituloLabel = new JLabel("GERENCIAMENTO DE MESAS");
        tituloLabel.setForeground(CoresUI.accent_color);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 56));
        posicionarLabelCentralizado(tituloLabel, 50, larguraTotalDaTela, 50);
        add(tituloLabel);

        String[] colunas = {"ID", "Capacidade", "Localização", "Status"};
        modeloTabelaMesas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaMesas = new JTable(modeloTabelaMesas);
        tabelaMesas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaMesas.setRowHeight(25);
        tabelaMesas.setSelectionBackground(CoresUI.accent_color.brighter());
        tabelaMesas.setSelectionForeground(CoresUI.primary_color);
        tabelaMesas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaMesas.getTableHeader().setBackground(CoresUI.secondary_color);
        tabelaMesas.getTableHeader().setForeground(CoresUI.text_color);
        tabelaMesas.getTableHeader().setReorderingAllowed(false);
        tabelaMesas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        scrollPaneTabela = new JScrollPane(tabelaMesas);
        scrollPaneTabela.setBounds(30, 150, larguraTotalDaTela - 60, 450);
        scrollPaneTabela.setBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1));
        add(scrollPaneTabela);

        Font fonteBotoes = new Font("SansSerif", Font.BOLD, 16);
        int botaoLargura = 180;
        int botaoAltura = 50;
        int yBotoes = 620;

        JButton btnAdicionar = new JButton("Adicionar Mesa");
        btnAdicionar.setBounds(30, yBotoes, botaoLargura, botaoAltura);
        btnAdicionar.setBackground(CoresUI.success_color);
        btnAdicionar.setForeground(CoresUI.primary_color);
        btnAdicionar.setFont(fonteBotoes);
        btnAdicionar.addActionListener(e -> adicionarNovaMesa());
        add(btnAdicionar);

        JButton btnEditar = new JButton("Editar Mesa");
        btnEditar.setBounds(30 + botaoLargura + 20, yBotoes, botaoLargura, botaoAltura);
        btnEditar.setBackground(CoresUI.accent_color);
        btnEditar.setForeground(CoresUI.primary_color);
        btnEditar.setFont(fonteBotoes);
        btnEditar.addActionListener(e -> editarMesaSelecionada());
        add(btnEditar);

        JButton btnRemover = new JButton("Remover Mesa");
        btnRemover.setBounds(30 + 2 * (botaoLargura + 20), yBotoes, botaoLargura, botaoAltura);
        btnRemover.setBackground(CoresUI.danger_color);
        btnRemover.setForeground(CoresUI.text_color);
        btnRemover.setFont(fonteBotoes);
        btnRemover.addActionListener(e -> removerMesaSelecionada());
        add(btnRemover);

        JButton btnAtualizar = new JButton("Atualizar Tabela");
        btnAtualizar.setBounds(30 + 3 * (botaoLargura + 20), yBotoes, botaoLargura, botaoAltura);
        btnAtualizar.setBackground(CoresUI.warning_color);
        btnAtualizar.setForeground(CoresUI.primary_color);
        btnAtualizar.setFont(fonteBotoes);
        btnAtualizar.addActionListener(e -> carregarMesasNaTabela());
        add(btnAtualizar);

        JButton voltarBotao = new JButton("Voltar");
        voltarBotao.setBounds(larguraTotalDaTela - 150 - 30, yBotoes, 150, botaoAltura);
        voltarBotao.setBackground(CoresUI.secondary_color);
        voltarBotao.setForeground(CoresUI.text_color);
        voltarBotao.setFont(fonteBotoes);
        voltarBotao.addActionListener(e -> {
            new GerenteUI().setVisible(true);
            dispose();
        });
        add(voltarBotao);

        carregarMesasNaTabela();
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

    private void adicionarNovaMesa() {
        JTextField campoCapacidade = new JTextField();
        JTextField campoLocalizacao = new JTextField();
        JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Disponivel", "Ocupada"});

        Object[] campos = {
                "Capacidade:", campoCapacidade,
                "Localização:", campoLocalizacao,
                "Status:", comboStatus
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Adicionar Nova Mesa", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String capacidadeStr = campoCapacidade.getText().trim();
            String localizacao = campoLocalizacao.getText().trim();
            String status = (String) comboStatus.getSelectedItem();

            if (capacidadeStr.isEmpty() || localizacao.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos para adicionar uma mesa.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int capacidade = Integer.parseInt(capacidadeStr);
                Mesa novaMesa = new Mesa(0, capacidade, localizacao, status);
                mesaDAO.inserirMesa(novaMesa);
                JOptionPane.showMessageDialog(this, "Mesa adicionada com sucesso!");
                carregarMesasNaTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacidade deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                // MySQL Error Code for Duplicate entry for key 'UNIQUE_KEY_NAME' is 1062
                // SQLState for integrity constraint violation is 23000
                if (ex.getSQLState().equals("23000") && ex.getErrorCode() == 1062) {
                    JOptionPane.showMessageDialog(this, "Erro: Já existe uma mesa com esta capacidade e localização.", "Erro de Duplicação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar mesa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar mesa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarMesaSelecionada() {
        int linhaSelecionada = tabelaMesas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma mesa na tabela para editar.", "Nenhuma Mesa Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMesa = (int) modeloTabelaMesas.getValueAt(linhaSelecionada, 0);
        int capacidadeAtual = (int) modeloTabelaMesas.getValueAt(linhaSelecionada, 1);
        String localizacaoAtual = (String) modeloTabelaMesas.getValueAt(linhaSelecionada, 2);
        String statusAtual = (String) modeloTabelaMesas.getValueAt(linhaSelecionada, 3);

        JTextField campoCapacidade = new JTextField(String.valueOf(capacidadeAtual));
        JTextField campoLocalizacao = new JTextField(localizacaoAtual);
        JComboBox<String> comboStatus = new JComboBox<>(new String[]{"Disponivel", "Ocupada"});
        comboStatus.setSelectedItem(statusAtual);

        Object[] campos = {
                "ID da Mesa:", idMesa,
                "Capacidade:", campoCapacidade,
                "Localização:", campoLocalizacao,
                "Status:", comboStatus
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Editar Mesa", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String capacidadeStr = campoCapacidade.getText().trim();
            String localizacao = campoLocalizacao.getText().trim();
            String status = (String) comboStatus.getSelectedItem();

            if (capacidadeStr.isEmpty() || localizacao.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos para editar uma mesa.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int capacidade = Integer.parseInt(capacidadeStr);
                Mesa mesaAtualizada = new Mesa(idMesa, capacidade, localizacao, status);
                mesaDAO.editarMesa(mesaAtualizada);
                JOptionPane.showMessageDialog(this, "Mesa editada com sucesso!");
                carregarMesasNaTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacidade deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                if (ex.getSQLState().equals("23000") && ex.getErrorCode() == 1062) {
                    JOptionPane.showMessageDialog(this, "Erro: Já existe outra mesa com esta capacidade e localização.", "Erro de Duplicação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao editar mesa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao editar mesa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerMesaSelecionada() {
        int linhaSelecionada = tabelaMesas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma mesa na tabela para remover.", "Nenhuma Mesa Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMesa = (int) modeloTabelaMesas.getValueAt(linhaSelecionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover a mesa ID: " + idMesa + "?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                mesaDAO.removerMesa(idMesa);
                JOptionPane.showMessageDialog(this, "Mesa removida com sucesso!");
                carregarMesasNaTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover mesa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}