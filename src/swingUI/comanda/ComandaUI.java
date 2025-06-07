package swingUI.comanda;

import model.Comanda;
import model.Mesa;
import model.Cardapio;
import service.ClienteDAO;
import service.ComandaDAO;
import service.MesaDAO;
import service.CardapioDAO;
import swingUI.constants.CoresUI;
import swingUI.constants.MainPainel;
import swingUI.garcom.GarcomUI;
import swingUI.gerente.GerenteUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ComandaUI extends MainPainel {

    private ComandaDAO comandaDAO;
    private MesaDAO mesaDAO;
    private CardapioDAO cardapioDAO;

    private JTable tabelaMesas;
    private DefaultTableModel modeloTabelaMesas;
    private JTable tabelaComandas;
    private DefaultTableModel modeloTabelaComandas;
    private JTable tabelaCardapio;
    private DefaultTableModel modeloTabelaCardapio;

    public ComandaUI(String userRole) {
        super("Painel de Comandas - ByteBistro", userRole);
        comandaDAO = new ComandaDAO();
        mesaDAO = new MesaDAO();
        cardapioDAO = new CardapioDAO();
        SwingUtilities.invokeLater(this::adicionarComponentesComanda);
    }

    private void adicionarComponentesComanda() {
        // MainPainel já configura o layout como null e o background do content pane.
        // Não é necessário chamar setLayout, setBackground ou setBorder diretamente aqui
        // no JFrame principal.

        // Obtém as dimensões atuais do content pane do JFrame (definidas por MainPainel)
        int frameWidth = getContentPane().getWidth();
        int frameHeight = getContentPane().getHeight();

        int margin = 20; // Margem geral para os painéis
        int currentY = margin; // Posição Y inicial

        // Título Centralizado
        JLabel tituloLabel = new JLabel("GERENCIAMENTO DE COMANDAS");
        tituloLabel.setForeground(CoresUI.accent_color);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        // Usa o método do MainPainel para centralizar e adicionar o título
        posicionarLabelCentralizado(tituloLabel, currentY, frameWidth - (2 * margin), 40);
        currentY += 40 + margin; // Atualiza a posição Y para o próximo componente

        // Painel Superior para Mesas e Cardápio
        JPanel painelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        painelSuperior.setBackground(CoresUI.primary_color);
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Borda interna do painel

        int panelSuperiorHeight = (frameHeight - (4 * margin) - 40 - 20 - 80) / 2; // Distribui o espaço vertical
        painelSuperior.setBounds(margin, currentY, frameWidth - (2 * margin), panelSuperiorHeight);
        getContentPane().add(painelSuperior);
        currentY += panelSuperiorHeight + margin; // Atualiza a posição Y

        // Tabela de Mesas dentro do painelSuperior
        String[] colunasMesas = {"ID Mesa", "Capacidade", "Localização", "Status"};
        modeloTabelaMesas = new DefaultTableModel(colunasMesas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMesas = new JTable(modeloTabelaMesas);
        configurarTabela(tabelaMesas);
        JScrollPane scrollMesas = new JScrollPane(tabelaMesas);
        scrollMesas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1), "Mesas", 0, 0, new Font("SansSerif", Font.BOLD, 14), CoresUI.accent_color));
        painelSuperior.add(scrollMesas);

        // Tabela de Cardápio dentro do painelSuperior
        String[] colunasCardapio = {"ID Item", "Nome Item", "Preço", "Categoria"};
        modeloTabelaCardapio = new DefaultTableModel(colunasCardapio, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCardapio = new JTable(modeloTabelaCardapio);
        configurarTabela(tabelaCardapio);
        JScrollPane scrollCardapio = new JScrollPane(tabelaCardapio);
        scrollCardapio.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1), "Cardápio", 0, 0, new Font("SansSerif", Font.BOLD, 14), CoresUI.accent_color));
        painelSuperior.add(scrollCardapio);

        // Painel Central para Comandas da Mesa Selecionada
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(CoresUI.primary_color);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // Borda interna do painel

        int panelCentralHeight = panelSuperiorHeight; // Mesma altura do painel superior
        painelCentral.setBounds(margin, currentY, frameWidth - (2 * margin), panelCentralHeight);
        getContentPane().add(painelCentral);
        currentY += panelCentralHeight + margin; // Atualiza a posição Y

        // Tabela de Comandas dentro do painelCentral
        String[] colunasComandas = {"ID Comanda", "ID Item", "ID Mesa", "Quantidade"};
        modeloTabelaComandas = new DefaultTableModel(colunasComandas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaComandas = new JTable(modeloTabelaComandas);
        configurarTabela(tabelaComandas);
        JScrollPane scrollComandas = new JScrollPane(tabelaComandas);
        scrollComandas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CoresUI.accent_color, 1), "Comandas da Mesa Selecionada", 0, 0, new Font("SansSerif", Font.BOLD, 14), CoresUI.accent_color));
        painelCentral.add(scrollComandas, BorderLayout.CENTER);

        // Painel de Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(CoresUI.primary_color);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Borda interna do painel

        int panelBotoesHeight = 80; // Altura fixa para os botões
        painelBotoes.setBounds(margin, currentY, frameWidth - (2 * margin), panelBotoesHeight);
        getContentPane().add(painelBotoes);

        Font fonteBotoes = new Font("SansSerif", Font.BOLD, 16);

        // Criação e adição dos botões usando o método auxiliar
        JButton btnAdicionar = criarBotao("Adicionar Item à Comanda", CoresUI.success_color, CoresUI.primary_color, fonteBotoes, e -> mostrarDialogoCadastroEdicao(null));
        JButton btnEditar = criarBotao("Editar Item da Comanda", CoresUI.accent_color, CoresUI.primary_color, fonteBotoes, e -> editarComandaSelecionada());
        JButton btnRemover = criarBotao("Remover Item da Comanda", CoresUI.danger_color, CoresUI.text_color, fonteBotoes, e -> removerComandaSelecionada());
        JButton btnVerComandasMesa = criarBotao("Ver Comandas da Mesa", CoresUI.secondary_color, CoresUI.text_color, fonteBotoes, e -> mostrarComandasMesaSelecionada());
        JButton btnPrecoTotalMesa = criarBotao("Preço Total da Mesa", CoresUI.secondary_color.darker(), CoresUI.text_color, fonteBotoes, e -> mostrarPrecoTotalMesa());
        JButton btnPagarComandaMesa = criarBotao("Pagar Comanda da Mesa", CoresUI.accent_color, CoresUI.primary_color, fonteBotoes, e -> pagarComandaMesa());
        JButton voltarBotao = criarBotao("Voltar", CoresUI.secondary_color, CoresUI.text_color, fonteBotoes, e -> {
            if ("gerente".equals(userRole)) {
                new GerenteUI().setVisible(true);
            } else {
                new GarcomUI().setVisible(true);
            }
            dispose();
        });

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnVerComandasMesa);
        painelBotoes.add(btnPrecoTotalMesa);
        painelBotoes.add(btnPagarComandaMesa);
        painelBotoes.add(voltarBotao);

        // Carrega os dados iniciais nas tabelas
        carregarMesasNaTabela();
        carregarCardapioNaTabela();

        // Adiciona o ListSelectionListener para a tabela de mesas
        tabelaMesas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Atualiza a tabela de comandas quando uma mesa é selecionada
                mostrarComandasMesaSelecionada();
            }
        });
    }

    // Método auxiliar para criar botões de forma consistente
    private JButton criarBotao(String texto, Color background, Color foreground, Font font, java.awt.event.ActionListener listener) {
        JButton button = new JButton(texto);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(font);
        button.setFocusPainted(false); // Remove o foco visual após o clique
        button.addActionListener(listener);
        return button;
    }

    // Configurações de estilo comuns para as tabelas
    private void configurarTabela(JTable table) {
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setSelectionBackground(CoresUI.accent_color.brighter());
        table.setSelectionForeground(CoresUI.primary_color);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(CoresUI.secondary_color);
        table.getTableHeader().setForeground(CoresUI.text_color);
        table.getTableHeader().setReorderingAllowed(false); // Impede reordenar colunas
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite selecionar apenas uma linha
    }

    private void carregarMesasNaTabela() {
        modeloTabelaMesas.setRowCount(0); // Limpa as linhas existentes
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

    private void carregarCardapioNaTabela() {
        modeloTabelaCardapio.setRowCount(0); // Limpa as linhas existentes
        try {
            ArrayList<Cardapio> itensCardapio = cardapioDAO.listarItens(); // Usando listarItens()
            for (Cardapio item : itensCardapio) {
                Object[] rowData = {item.getId_item(), item.getNome_item(), String.format("%.2f", item.getPreco()), item.getCategoria()};
                modeloTabelaCardapio.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cardápio: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mostra as comandas da mesa atualmente selecionada na tabela de mesas
    private void mostrarComandasMesaSelecionada() {
        int linhaSelecionada = tabelaMesas.getSelectedRow();
        if (linhaSelecionada == -1) {
            modeloTabelaComandas.setRowCount(0); // Limpa a tabela se nenhuma mesa estiver selecionada
            return;
        }

        int idMesa = (int) modeloTabelaMesas.getValueAt(linhaSelecionada, 0);
        carregarComandasDaMesaSelecionada(idMesa);
    }

    // Carrega as comandas para um ID de mesa específico
    private void carregarComandasDaMesaSelecionada(int idMesa) {
        modeloTabelaComandas.setRowCount(0); // Limpa as linhas existentes
        try {
            ArrayList<Comanda> comandas = comandaDAO.buscarComandasPorMesa(idMesa);
            for (Comanda comanda : comandas) {
                Object[] rowData = {comanda.getId_comanda(), comanda.getId_item(), comanda.getId_mesa(), comanda.getQnt_item()};
                modeloTabelaComandas.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar comandas da mesa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Exibe o diálogo para adicionar ou editar uma comanda
    private void mostrarDialogoCadastroEdicao(Comanda comandaParaEditar) {
        // Campos do diálogo são criados localmente
        JTextField campoIdItem = new JTextField();
        JTextField campoIdMesa = new JTextField();
        JTextField campoQuantidade = new JTextField();

        if (comandaParaEditar != null) {
            // Preenche os campos para edição
            campoIdItem.setText(String.valueOf(comandaParaEditar.getId_item()));
            campoIdMesa.setText(String.valueOf(comandaParaEditar.getId_mesa()));
            campoQuantidade.setText(String.valueOf(comandaParaEditar.getQnt_item()));
            campoIdMesa.setEditable(false); // Não permite mudar a mesa ao editar
            campoIdItem.setEditable(false); // Não permite mudar o item ao editar
        } else {
            // Pré-preenche ID da mesa se uma mesa estiver selecionada
            int linhaMesaSelecionada = tabelaMesas.getSelectedRow();
            if (linhaMesaSelecionada != -1) {
                int idMesaSelecionada = (int) modeloTabelaMesas.getValueAt(linhaMesaSelecionada, 0);
                campoIdMesa.setText(String.valueOf(idMesaSelecionada));
                campoIdMesa.setEditable(false);
            }

            // Pré-preenche ID do item se um item do cardápio estiver selecionado
            int linhaCardapioSelecionado = tabelaCardapio.getSelectedRow();
            if (linhaCardapioSelecionado != -1) {
                int idItemSelecionado = (int) modeloTabelaCardapio.getValueAt(linhaCardapioSelecionado, 0);
                campoIdItem.setText(String.valueOf(idItemSelecionado));
                campoIdItem.setEditable(false);
            }
        }

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 10, 10));
        painelDialogo.add(new JLabel("ID Item (do Cardápio):"));
        painelDialogo.add(campoIdItem);
        painelDialogo.add(new JLabel("ID Mesa:"));
        painelDialogo.add(campoIdMesa);
        painelDialogo.add(new JLabel("Quantidade:"));
        painelDialogo.add(campoQuantidade);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo,
                comandaParaEditar == null ? "Adicionar Item à Comanda" : "Editar Item da Comanda",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int idItem = Integer.parseInt(campoIdItem.getText().trim());
                int idMesa = Integer.parseInt(campoIdMesa.getText().trim());
                int quantidade = Integer.parseInt(campoQuantidade.getText().trim());

                if (quantidade <= 0) {
                    throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
                }

                // Valida se ID do item e da mesa existem usando listarItens e listarMesas
                if (cardapioDAO.listarItens().stream().noneMatch(item -> item.getId_item() == idItem)) {
                    JOptionPane.showMessageDialog(this, "ID do Item não encontrado no Cardápio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (mesaDAO.listarMesas().stream().noneMatch(mesa -> mesa.getId_mesa() == idMesa)) {
                    JOptionPane.showMessageDialog(this, "ID da Mesa não encontrada.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (comandaParaEditar == null) {
                    // Lógica para adicionar novo item ou somar quantidade se já existir
                    Comanda comandaExistente = null;
                    ArrayList<Comanda> comandasMesa = comandaDAO.buscarComandasPorMesa(idMesa);
                    for (Comanda c : comandasMesa) {
                        if (c.getId_item() == idItem) {
                            comandaExistente = c;
                            break;
                        }
                    }

                    if (comandaExistente != null) {
                        int novaQuantidade = comandaExistente.getQnt_item() + quantidade;
                        comandaExistente.setQnt_item(novaQuantidade);
                        comandaDAO.atualizarComanda(comandaExistente);
                        JOptionPane.showMessageDialog(this, "Quantidade do item na comanda atualizada com sucesso!");
                    } else {
                        Comanda novaComanda = new Comanda(0, idItem, idMesa, quantidade);
                        comandaDAO.cadastrarComanda(novaComanda);
                        JOptionPane.showMessageDialog(this, "Item adicionado à comanda com sucesso!");
                    }
                } else {
                    // Lógica para editar a quantidade de uma comanda existente
                    comandaParaEditar.setQnt_item(quantidade);
                    comandaDAO.atualizarComanda(comandaParaEditar);
                    JOptionPane.showMessageDialog(this, "Comanda atualizada com sucesso!");
                }
                // Recarrega a tabela de comandas da mesa selecionada após a operação
                mostrarComandasMesaSelecionada();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID do Item, ID da Mesa e Quantidade devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Prepara o diálogo para editar uma comanda selecionada
    private void editarComandaSelecionada() {
        int linhaSelecionada = tabelaComandas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma comanda na tabela para editar.", "Nenhuma Comanda Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém os dados da linha selecionada
        int idComanda = (int) modeloTabelaComandas.getValueAt(linhaSelecionada, 0);
        int idItem = (int) modeloTabelaComandas.getValueAt(linhaSelecionada, 1);
        int idMesa = (int) modeloTabelaComandas.getValueAt(linhaSelecionada, 2);
        int quantidade = (int) modeloTabelaComandas.getValueAt(linhaSelecionada, 3);

        Comanda comandaParaEditar = new Comanda(idComanda, idItem, idMesa, quantidade);
        mostrarDialogoCadastroEdicao(comandaParaEditar);
    }

    // Remove a comanda selecionada da tabela e do banco de dados
    private void removerComandaSelecionada() {
        int linhaSelecionada = tabelaComandas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item da comanda na tabela para remover.", "Nenhum Item Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idComanda = (int) modeloTabelaComandas.getValueAt(linhaSelecionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o item da comanda ID: " + idComanda + "?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                comandaDAO.removerComanda(idComanda);
                JOptionPane.showMessageDialog(this, "Item da comanda removido com sucesso!");
                mostrarComandasMesaSelecionada(); // Recarrega a tabela de comandas da mesa selecionada
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover item da comanda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Exibe o preço total da comanda da mesa selecionada
    private void mostrarPrecoTotalMesa() {
        int linhaSelecionada = tabelaMesas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma mesa para calcular o preço total.", "Nenhuma Mesa Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMesa = (int) modeloTabelaMesas.getValueAt(linhaSelecionada, 0);
        try {
            double precoTotal = comandaDAO.comandaMesaPrecoTotal(idMesa);
            JOptionPane.showMessageDialog(this, String.format("O preço total da comanda para a mesa ID %d é: R$ %.2f", idMesa, precoTotal), "Preço Total da Mesa", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular preço total: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Paga a comanda da mesa selecionada, removendo todos os seus itens
    private void pagarComandaMesa() {
        int linhaSelecionada = tabelaMesas.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma mesa para pagar a comanda.", "Nenhuma Mesa Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMesa = (int) modeloTabelaMesas.getValueAt(linhaSelecionada, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja pagar e remover todas as comandas da mesa ID: " + idMesa + "?", "Confirmar Pagamento", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                comandaDAO.removerComandasMesa(idMesa);
                JOptionPane.showMessageDialog(this, "Comanda da mesa ID " + idMesa + " paga e removida com sucesso!");
                mostrarComandasMesaSelecionada(); // Recarrega a tabela de comandas da mesa selecionada (agora vazia)
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao pagar comanda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}