package service;

import model.Reserva;

import java.sql.*;
import java.util.ArrayList;

public class ReservaDAO {

    public void cadastrarReserva(Reserva reserva) {
        String sql = "INSERT INTO Reserva (id_cliente, id_mesa, data_hora, num_pessoas) VALUES (?,?,?,?)";

        try(Connection conexao = BancoConfig.criarConexao();
            PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setInt(1, reserva.getId_cliente());
            ps.setInt(2, reserva.getId_mesa());
            ps.setTimestamp(3, reserva.getData_hora());
            ps.setInt(4, reserva.getNum_pessoas());
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("Erro ao cadastrar reserva: " + e.getMessage());
        }
    }

    public ArrayList<Reserva> listarReservas(){
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reserva";

        try(Connection conexao = BancoConfig.criarConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                int id_reserva = rs.getInt("id_reserva");
                int id_cliente = rs.getInt("id_cliente");
                int id_mesa = rs.getInt("id_mesa");
                Timestamp data_hora = rs.getTimestamp("data_hora");
                int num_pessoas = rs.getInt("num_pessoas");
                Reserva reserva = new Reserva(id_reserva, id_cliente, id_mesa, data_hora, num_pessoas);
                reservas.add(reserva);
            }
        }catch (SQLException e){
            System.err.println("Erro ao listar reservas" + e.getMessage());
        }
        return reservas;
    }

    public void atualizarReserva(Reserva reservaAtualizada) {
        String sql = "UPDATE Reserva SET id_cliente = ?, id_mesa = ?, data_hora = ?, num_pessoas = ? WHERE id_reserva = ?";

        try(Connection conexao = BancoConfig.criarConexao();
            PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setInt(1, reservaAtualizada.getId_cliente());
            ps.setInt(2, reservaAtualizada.getId_mesa());
            ps.setTimestamp(3, reservaAtualizada.getData_hora());
            ps.setInt(4, reservaAtualizada.getNum_pessoas());
            ps.setInt(5, reservaAtualizada.getId_reserva());

            int linhasAfetadas = ps.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Reserva atualizada.");
            }else{
                System.out.println("Nenhuma reserva foi modificada.");
            }
        }catch(SQLException e){
            System.err.println("Erro ao atualizar reserva: " + e.getMessage());
        }
    }

    public void removerReserva(int id_reserva) {
        String sql = "DELETE FROM Reserva WHERE id_reserva = ?";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setInt(1, id_reserva);
            int linhasAfetadas = ps.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Reserva removida.");
            } else{
                System.out.println("Nenhuma reserva foi removida.");
            }

        }catch (SQLException e){
            System.err.println("Erro ao remover reserva: " + e.getMessage());
        }
    }
}
