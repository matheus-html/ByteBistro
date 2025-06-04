package service;

import com.sun.security.jgss.GSSUtil;
import constantes.BancoConfig;
import constantes.Cardapio;

import javax.smartcardio.Card;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardapioDAO {

    public void inserirItem(Cardapio itemCardapio) {
        String sql = "INSERT INTO Cardapio (nome_item, descricao, preco, categoria) VALUES (?,?,?,?)";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);

            ps.setString(1,itemCardapio.getNome_item());
            ps.setString(2,itemCardapio.getDescricao());
            ps.setDouble(3,itemCardapio.getPreco());
            ps.setString(4,itemCardapio.getCategoria());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (ps != null){
                    ps.close();
                }
                if (conexao != null){
                    conexao.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Cardapio> listarItens(){
        ArrayList<Cardapio> itensCardapio = new ArrayList<Cardapio>();

        String sql = "SELECT * FROM Cardapio";

        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String nome_item = rs.getString(2);
                String descricao = rs.getString(3);
                double preco = rs.getDouble(4);
                String categoria = rs.getString(5);

                Cardapio itemCardapio = new Cardapio(id, nome_item, descricao, preco, categoria);
                itensCardapio.add(itemCardapio);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (rs != null){
                    rs.close();
                }
                if (ps != null){
                    ps.close();
                }
                if (conexao != null){
                    conexao.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return itensCardapio;
    }

    public void alterarItem(Cardapio itemCardapio) {
        String sql = "UPDATE Cardapio SET nome_item = ?, descricao = ?, preco = ?, categoria = ? WHERE id = ?";

        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);

            ps.setString(1,itemCardapio.getNome_item());
            ps.setString(2,itemCardapio.getDescricao());
            ps.setDouble(3,itemCardapio.getPreco());
            ps.setString(4,itemCardapio.getCategoria());
            ps.setInt(5, itemCardapio.getId_item());

            int retorno = ps.executeUpdate();

            if (retorno > 0){
                System.out.println("Item foi alterado.");
            } else {
                System.out.println("Erro ao alterar item.");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (ps != null){
                    ps.close();
                }
                if (conexao != null){
                    conexao.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void excluirItem(int id) {
        String sql = "DELETE FROM Cardapio WHERE id = ?";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1,id);

            int retorno = ps.executeUpdate();
            if (retorno > 0){
                System.out.println("Item excluido.");
            } else {
                System.out.println("Erro ao excluir item.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if (ps != null){
                    ps.close();
                }
                if (conexao != null){
                    conexao.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
