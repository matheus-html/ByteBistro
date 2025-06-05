package service;

import constantes.BancoConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private static Connection conn;
    public static String ValidarLogin(String username, String password) {
        try{
            conn = BancoConfig.criarConexao();

            PreparedStatement validarUsuario = conn.prepareStatement(
                    "SELECT * FROM Usuario WHERE NOMEUSUARIO = ? AND SENHA = ?"
            );
            validarUsuario.setString(1, username);
            validarUsuario.setString(2, password);

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
