package constantes;

import java.sql.*;

public class BancoConfig {
    public static final String tabUsuario = "Usuario";

    public static Connection criarConexao() throws SQLException {
        try{
            String dbUrl = "jdbc:mysql://127.0.0.1:3306/bytebistro";
            String dbUser = "root";
            String dbPassword = "aninha123";

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch(ClassNotFoundException e){
            throw new SQLException("Driver JDBC não encontrado!"+e);
        }
    }

    public static void criarBanco() throws SQLException {
        String cliente = "CREATE TABLE IF NOT EXISTS Cliente(\n" +
                "\tid_cliente INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nome_cliente VARCHAR(100) NOT NULL,\n" +
                "    cpf VARCHAR(100) UNIQUE NOT NULL,\n" +
                "    telefone VARCHAR(100) NOT NULL,\n" +
                "    email VARCHAR(100) UNIQUE NOT NULL\n" +
                ");";
        String mesa = "CREATE TABLE IF NOT EXISTS Mesa(\n" +
                "\tid_mesa INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    capacidade INT NOT NULL,\n" +
                "    localizacao VARCHAR(50) NOT NULL,\n" +
                "\tstatus ENUM('disponivel', 'ocupada', 'manutencao') \n" +
                "    DEFAULT 'disponivel'\n" +
                ");";
        String reserva = "CREATE TABLE IF NOT EXISTS Reserva(\n" +
                "\tid_reserva INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    id_cliente INT NOT NULL,\n" +
                "    id_mesa INT NOT NULL,\n" +
                "    data_hora DATETIME NOT NULL,\n" +
                "    num_pessoas INT NOT NULL,\n" +
                "    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),\n" +
                "    FOREIGN KEY (id_mesa) REFERENCES Mesa(id_mesa),\n" +
                "    CONSTRAINT reserva_mesa_data UNIQUE (id_mesa, data_hora)\n" +
                ");";
        String cardapio = "CREATE TABLE IF NOT EXISTS Cardapio(\n" +
                "\tid_item INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nome VARCHAR(100) NOT NULL,\n" +
                "    descricao TEXT,\n" +
                "    preco DECIMAL(10, 2) NOT NULL,\n" +
                "    categoria VARCHAR(100) NOT NULL\n" +
                ");";

        Statement stmt = criarConexao().createStatement();
        stmt.execute(cliente);
        stmt.execute(mesa);
        stmt.execute(reserva);
        stmt.execute(cardapio);
    }
}
