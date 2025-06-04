package service;

import constantes.BancoConfig;
import constantes.Mesa;

import java.sql.*;
import java.util.ArrayList;

public class MesaDAO {

    public void inserirMesa(Mesa mesa){
        String sql = "INSERT INTO Mesa (capacidade, localizacao, statusMesa) VALUES (?,?,?)";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);

            ps.setInt(1, mesa.getCapacidade());
            ps.setString(2, mesa.getLocalizacao());
            ps.setString(3, mesa.getStatusMesa());
            ps.execute();

        } catch (SQLException e) {
            System.err.println("Houve um erro ao inserir mesa: " + e.getMessage());
        } finally {
            try{
                if(ps != null){
                    ps.close();
                }
                if(conexao != null){
                    conexao.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Mesa> listarMesas(){
        ArrayList<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT * FROM Mesa";

        Connection conexao = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            conexao = BancoConfig.criarConexao();
            st = conexao.createStatement();
            rs = st.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id_mesa");
                int capacidade = rs.getInt("capacidade");
                String localizacao = rs.getString("localizacao");
                String statusMesa = rs.getString("statusMesa");

                Mesa mesa = new Mesa(id, capacidade, localizacao, statusMesa);
                mesas.add(mesa);

                System.out.println("ID:" + id +
                        " Capacidade: "+ capacidade +
                        " Localização: "+ localizacao +
                        " Status: "+ statusMesa);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if(rs != null){
                    rs.close();
                }
                if(st != null){
                    st.close();
                }
                if(conexao != null){
                    conexao.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

            if(mesas.isEmpty()){
                System.out.println("Nenhuma mesa registrada.");
            }
        }
        return mesas;
    }

    public void editarMesa(Mesa mesa){
        String sql = "UPDATE Mesa SET capacidade = ?, " + "localizacao = ?, " + "statusMesa = ? WHERE id_mesa = ?";

        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);

            ps.setInt(1, mesa.getCapacidade());
            ps.setString(2, mesa.getLocalizacao());
            ps.setString(3, mesa.getStatusMesa());
            ps.setInt(4, mesa.getId_mesa());

            int retorno = ps.executeUpdate();

            if(retorno > 0){
                System.out.println("Mesa editada.");
            } else {
                System.out.println("Não foi possível editar a mesa.");
            }

        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            try{
                if(ps != null){
                    ps.close();
                }
                if(conexao != null){
                    conexao.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void removerMesa(int id_mesa){
        String sql = "DELETE FROM Mesa WHERE id_mesa = ?";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);

            ps.setInt(1, id_mesa);

            int retorno = ps.executeUpdate();

            if(retorno > 0){
                System.out.println("Mesa foi removida.");
            } else {
                System.out.println("Nenhuma mesa foi removida.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally{
            try {
                if(ps != null){
                    ps.close();
                }
                if(conexao != null){
                    conexao.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
