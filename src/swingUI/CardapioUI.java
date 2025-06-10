package swingUI;

import model.Cardapio;
import service.CardapioDAO;
import swingUI.constantes.CoresUI;
import swingUI.constantes.MainPainel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CardapioUI extends MainPainel {

    private JTable tabelaCardapio;
    private DefaultTableModel modeloTabela;
    private CardapioDAO cardapioDAO;
    private JComboBox<String> comboCategorias;

    public CardapioUI() {
        super("MainPainel Cardápio - ByteBistro");
        cardapioDAO = new CardapioDAO();
        SwingUtilities.invokeLater(this::addComponentesCardapio);
    }

    public void addComponentesCardapio(){

        int larguraTotalDaTela = getContentPane().getWidth();

        JLabel tituloLabel = new JLabel("GERENCIAMENTO DE CARDÁPIO");
        tituloLabel.setForeground(CoresUI.accent_color);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 56));
        posicionarLabelCentralizado(tituloLabel, 50, larguraTotalDaTela, 50);

        String[] categorias = {"Todos", "Entradas", "Pratos Principais", "Bebidas", "Sobremesas", "Pizzas", "Massas", "Salgados", "Doces"};
        comboCategorias = new JComboBox<>(categorias);
        comboCategorias.setBounds(30, 110, 200, 30);
        comboCategorias.setFont(new Font("SansSerif", Font.PLAIN, 16));
        comboCategorias.addActionListener(e -> carregarItensNaTabela());
        add(comboCategorias);

        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Preço");
        modeloTabela.addColumn("Categoria");

        tabelaCardapio = new JTable(modeloTabela);
        tabelaCardapio.getTableHeader().setReorderingAllowed(false);
        tabelaCardapio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaCardapio);
        scrollPane.setBounds(30, 150, larguraTotalDaTela - 60, 450);
        add(scrollPane);

        Font fonteBotoes = new Font("SansSerif", Font.BOLD, 16);

        JButton btnRefresh = new JButton("Atualizar Tabela");
        btnRefresh.setBounds(30, 620, 180, 50);
        btnRefresh.setBackground(CoresUI.warning_color);
        btnRefresh.setForeground(CoresUI.primary_color);
        btnRefresh.setFont(fonteBotoes);
        btnRefresh.addActionListener(e -> carregarItensNaTabela());
        add(btnRefresh);

        JButton btnAdicionar = new JButton("Adicionar Item");
        btnAdicionar.setBounds(220, 620, 180, 50);
        btnAdicionar.setBackground(CoresUI.success_color);
        btnAdicionar.setForeground(CoresUI.text_color);
        btnAdicionar.setFont(fonteBotoes);
        btnAdicionar.addActionListener(e -> adicionarItem());
        add(btnAdicionar);

        JButton btnEditar = new JButton("Editar Item");
        btnEditar.setBounds(410, 620, 180, 50);
        btnEditar.setBackground(CoresUI.accent_color);
        btnEditar.setForeground(CoresUI.primary_color);
        btnEditar.setFont(fonteBotoes);
        btnEditar.addActionListener(e -> editarItem());
        add(btnEditar);

        JButton btnRemover = new JButton("Remover Item");
        btnRemover.setBounds(600, 620, 180, 50);
        btnRemover.setBackground(CoresUI.danger_color);
        btnRemover.setForeground(CoresUI.text_color);
        btnRemover.setFont(fonteBotoes);
        btnRemover.addActionListener(e -> removerItem());
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

        carregarItensNaTabela();
    }

    private void carregarItensNaTabela(){
        modeloTabela.setRowCount(0);
        ArrayList<Cardapio> itens;
        String categoriaSelecionada = (String) comboCategorias.getSelectedItem();

        if ("Todos".equals(categoriaSelecionada)) {
            itens = cardapioDAO.listarItens();
        } else {
            itens = cardapioDAO.listarItensPorCategoria(categoriaSelecionada);
        }

        for (Cardapio item : itens) {
            modeloTabela.addRow(new Object[]{
                    item.getId_item(),
                    item.getNome_item(),
                    item.getDescricao(),
                    item.getPreco(),
                    item.getCategoria()
            });
        }
    }

    private void adicionarItem() {
        JTextField campoNome = new JTextField();
        JTextField campoDescricao = new JTextField();
        JTextField campoPreco = new JTextField();
        String[] categorias = {"Entradas", "Pratos Principais", "Bebidas", "Sobremesas", "Pizzas", "Massas", "Salgados", "Doces"};
        JComboBox<String> comboCategoriasDialog = new JComboBox<>(categorias);

        Object[] campos = {
                "Nome:", campoNome,
                "Descrição:", campoDescricao,
                "Preço:", campoPreco,
                "Categoria:", comboCategoriasDialog
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Adicionar Novo Item ao Cardápio", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText().trim();
            String descricao = campoDescricao.getText().trim();
            String precoStr = campoPreco.getText().trim();
            String categoria = (String) comboCategoriasDialog.getSelectedItem();

            if (nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty() || categoria == null) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double preco = Double.parseDouble(precoStr);
                if (preco <= 0) {
                    JOptionPane.showMessageDialog(this, "O preço deve ser um valor positivo.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Cardapio novoItem = new Cardapio(nome, descricao, preco, categoria);
                cardapioDAO.inserirItem(novoItem);
                carregarItensNaTabela();
                JOptionPane.showMessageDialog(this, "Item adicionado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void editarItem() {
        int linhaSelecionada = tabelaCardapio.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para editar.");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String descricaoAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        double precoAtual = (double) modeloTabela.getValueAt(linhaSelecionada, 3);
        String categoriaAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 4);

        JTextField campoNome = new JTextField(nomeAtual);
        JTextField campoDescricao = new JTextField(descricaoAtual);
        JTextField campoPreco = new JTextField(String.valueOf(precoAtual));
        String[] categorias = {"Entradas", "Pratos Principais", "Bebidas", "Sobremesas", "Pizzas", "Massas", "Salgados", "Doces"};
        JComboBox<String> comboCategoriasDialog = new JComboBox<>(categorias);
        comboCategoriasDialog.setSelectedItem(categoriaAtual);

        Object[] campos = {
                "Nome:", campoNome,
                "Descrição:", campoDescricao,
                "Preço:", campoPreco,
                "Categoria:", comboCategoriasDialog
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Editar Item do Cardápio", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText().trim();
            String descricao = campoDescricao.getText().trim();
            String precoStr = campoPreco.getText().trim();
            String categoria = (String) comboCategoriasDialog.getSelectedItem();

            if (nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty() || categoria == null) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double preco = Double.parseDouble(precoStr);
                if (preco <= 0) {
                    JOptionPane.showMessageDialog(this, "O preço deve ser um valor positivo.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Cardapio itemEditado = new Cardapio(id, nome, descricao, preco, categoria);
                cardapioDAO.alterarItem(itemEditado);
                carregarItensNaTabela();
                JOptionPane.showMessageDialog(this, "Item editado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void removerItem() {
        int linhaSelecionada = tabelaCardapio.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o item selecionado?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            cardapioDAO.excluirItem(id);
            carregarItensNaTabela();
            JOptionPane.showMessageDialog(this, "Item removido com sucesso!");
        }
    }
}