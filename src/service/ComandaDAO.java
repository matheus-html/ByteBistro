package service;

import model.Comanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComandaDAO {
    public void cadastrarComanda(Comanda comanda) {
        String sql = "INSERT INTO Comanda (id_item, id_mesa, qnt_item) VALUES (?,?,?)";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, comanda.getId_item());
            ps.setInt(2, comanda.getId_mesa());
            ps.setInt(3, comanda.getQnt_item());
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("Erro ao cadastrar comanda." + e.getMessage());
        }
    }

    public ArrayList<Comanda> listarComandas() {
        ArrayList<Comanda> comandas = new ArrayList<>();
        String sql = "SELECT * FROM Comanda";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                int id_comanda = rs.getInt("id_comanda");
                int id_item = rs.getInt("id_item");
                int id_mesa = rs.getInt("id_mesa");
                int qnt_item = rs.getInt("qnt_item");

                Comanda comanda = new Comanda(id_comanda, id_item, id_mesa, qnt_item);
                comandas.add(comanda);
            }

        }catch (SQLException e){
            System.err.println("Erro ao listar comandas." + e.getMessage());
        }
        return comandas;
    }

    public void atualizarComanda(Comanda comanda) {
        String sql = "UPDATE Comanda SET qnt_item = ? WHERE id_comanda = ?";

        try(Connection conexao = BancoConfig.criarConexao();
            PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, comanda.getQnt_item());
            ps.setInt(2, comanda.getId_comanda());
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("Erro ao atualizar comanda." + e.getMessage());
        }
    }

    public ArrayList<Comanda> buscarComandasPorMesa(int id_mesa) {
        ArrayList<Comanda> comandasIdMesa = new ArrayList<>();
        String sql = "SELECT * FROM Comanda WHERE id_mesa = ?";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id_mesa);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int id_comanda = rs.getInt("id_comanda");
                    int id_item = rs.getInt("id_item");
                    int qnt_item = rs.getInt("qnt_item");

                    Comanda comanda = new Comanda(id_comanda,id_item, id_mesa, qnt_item);
                    comandasIdMesa.add(comanda);
                }
            }
        }catch (SQLException e){
            System.err.println("Erro ao listar comandas." + e.getMessage());
        }
        return comandasIdMesa;
    }

    public double comandaMesaPrecoTotal(int id_mesa){
        String sql = "SELECT SUM(car.preco * com.qnt_item) FROM Cardapio car " +
                "JOIN Comanda com ON com.id_item = car.id_item WHERE " +
                "com.id_mesa = ?";

        double precoTotal = 0;

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id_mesa);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    precoTotal = rs.getDouble(1);
                }
            }
        }catch (SQLException e){
            System.err.println("Erro ao listar comandas." + e.getMessage());
        }
        return precoTotal;
    }

    public void removerComanda(int id_comanda) {
        String sql = "DELETE FROM Comanda WHERE id_comanda = ?";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id_comanda);

            int linhasAfetadas = ps.executeUpdate();
            if(linhasAfetadas > 0){
                System.out.println("Comanda removida.");
            } else {
                System.out.println("Nenhuma comanda encontrada com esse ID.");
            }

        }catch (SQLException e){
            System.err.println("Erro ao remover comanda." + e.getMessage());
        }

    }

    public void removerComandasMesa(int id_mesa){
        String sql = "DELETE FROM Comanda WHERE id_mesa = ?";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id_mesa);

            int linhasAfetadas = ps.executeUpdate();
            if(linhasAfetadas > 0){
                System.out.println("Comanda(s) removida(s).");
            } else {
                System.out.println("Nenhuma comanda encontrada para essa mesa.");
            }

        }catch (SQLException e){
            System.err.println("Erro ao remover comanda(s)." + e.getMessage());
        }
    }
}
