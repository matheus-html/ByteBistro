package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private static Connection conn;
    public static String ValidarLogin(String nome_usuario, String senha) {
        try{
            conn = BancoConfig.criarConexao();

            PreparedStatement validarUsuario = conn.prepareStatement(
                    "SELECT * FROM Usuario WHERE NOMEUSUARIO = ? AND SENHA = ?"
            );
            validarUsuario.setString(1, nome_usuario);
            validarUsuario.setString(2, senha);

            ResultSet rs = validarUsuario.executeQuery();

            if(rs.next()){
                return rs.getString("role");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
