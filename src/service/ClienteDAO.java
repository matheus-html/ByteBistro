package service;

import constantes.BancoConfig;
import constantes.Cliente;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    public void inserirCliente(Cliente cliente){
        String sql = "INSERT INTO Cliente (nome_cliente, cpf, telefone, email) VALUES (?,?,?,?)";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);

            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            ps.executeUpdate();

        } catch(SQLException e){
            System.err.println("Houve um erro ao salvar cliente: " + e.getMessage());
        } finally{
            try{
                if(ps != null){
                    ps.close();
                }
                if(conexao != null){
                    conexao.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        Connection conexao = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            conexao = BancoConfig.criarConexao();
            st = conexao.createStatement();
            rs = st.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id_cliente");
                String nomeCliente = rs.getString("nome_cliente");
                String cpf= rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                Cliente cliente = new Cliente(id, nomeCliente, cpf, telefone, email);
                clientes.add(cliente);

                System.out.println("ID: " + id +
                        ", Nome: " + nomeCliente +
                        ", CPF: " + cpf +
                        ", Telefone: " + telefone +
                        ", Email: " + email);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (st != null){
                    st.close();
                }
                if (conexao != null){
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(clientes.isEmpty()){
            System.out.println("Nenhum cliente registrado.");
        }
        }
        return clientes;
    }


    public void editarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente SET nome_cliente = ? , " + "cpf = ?, " + "telefone = ?, " + "email = ? WHERE id_cliente = ?";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);
            ps.setString(1, cliente.getNomeCliente());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            ps.setInt(5, cliente.getId());

            int retorno = ps.executeUpdate();

            if(retorno > 0){
                System.out.println("Cliente foi atualizado.");
            } else{
                System.out.println("Nenhum registro foi modificado.");
            }
        } catch(SQLException e){
            System.err.println("Houve um erro ao atualizar cliente: " + e.getMessage());
        } finally{
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

    public void removerCliente(int id_cliente){
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        Connection conexao = null;
        PreparedStatement ps = null;

        try{
            conexao = BancoConfig.criarConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id_cliente);

            int retorno = ps.executeUpdate();

            if(retorno > 0){
                System.out.println("Cliente removido.");
            } else {
                System.out.println("Nenhum registro foi removido.");
            }
        } catch (SQLException e) {
            System.err.println("Houve um erro ao remover cliente: " + e.getMessage());
        } finally {
            try{
                if(ps != null){
                    ps.close();
                }
                if (conexao != null){
                    conexao.close();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
