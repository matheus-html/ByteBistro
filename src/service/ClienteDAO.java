package service;

import model.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    public boolean inserirCliente(Cliente cliente) throws SQLException{
        String sql = "INSERT INTO Cliente (nome_cliente, cpf, telefone, email) VALUES (?,?,?,?)";

        try(Connection conexao = BancoConfig.criarConexao();
        PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch(SQLException e){
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
            throw e;
        }
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try(Connection conexao = BancoConfig.criarConexao();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                int id = rs.getInt("id_cliente");
                String nomeCliente = rs.getString("nome_cliente");
                String cpf= rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                Cliente cliente = new Cliente(id, nomeCliente, cpf, telefone, email);
                clientes.add(cliente);
            }
        } catch (SQLException e){
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public boolean atualizarCliente(Cliente clienteAtualizado) throws SQLException {
        String sql = "UPDATE Cliente SET nome_cliente = ? , cpf = ?, telefone = ?, email = ? WHERE id_cliente = ?";

        try(Connection conexao = BancoConfig.criarConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, clienteAtualizado.getNomeCliente());
            ps.setString(2, clienteAtualizado.getCpf());
            ps.setString(3, clienteAtualizado.getTelefone());
            ps.setString(4, clienteAtualizado.getEmail());
            ps.setInt(5, clienteAtualizado.getId());

            int linhasAfetadas = ps.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Cliente atualizado.");
                return true;
            } else{
                System.out.println("Nenhum cliente foi modificado.");
                return false;
            }
        } catch(SQLException e){
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            throw e;
        }
    }

    public void removerCliente(int id_cliente){
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";

        try(Connection conexao = BancoConfig.criarConexao();
            PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setInt(1, id_cliente);

            int linhasAfetadas = ps.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Cliente removido.");
            } else {
                System.out.println("Nenhum registro foi removido.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover cliente: " + e.getMessage());
        }
    }
}
