package constantes;

import java.sql.*;

public class BancoConfig {

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

    public static void criarTabelas() throws SQLException {
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
                "\tstatusMesa ENUM ('ocupada', 'disponivel') NOT NULL DEFAULT 'disponivel',\n" +
                "    CONSTRAINT mesa_capacidade_localizacao UNIQUE (capacidade, localizacao)\n" +
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
                "    nome_item VARCHAR(100) NOT NULL UNIQUE\t,\n" +
                "    descricao TEXT,\n" +
                "    preco DECIMAL(10, 2) NOT NULL CHECK (preco > 0),\n" +
                "    categoria VARCHAR(100) NOT NULL\n" +
                ");";

        String comanda = "CREATE TABLE IF NOT EXISTS Comanda(\n" +
                "\tid_comanda INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    id_item INT NOT NULL,\n" +
                "    id_mesa INT NOT NULL,\n" +
                "\tqnt_item INT NOT NULL,\n" +
                "    FOREIGN KEY (id_item) REFERENCES Cardapio(id_item),\n" +
                "    FOREIGN KEY (id_mesa) REFERENCES Mesa(id_mesa),\n" +
                "    CONSTRAINT comanda_item_mesa UNIQUE (id_item, id_mesa)\n" +
                ");";

        String Usuario = "CREATE TABLE IF NOT EXISTS USUARIO (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nome VARCHAR(100) NOT NULL,\n" +
                "    nomeUsuario VARCHAR(50) NOT NULL UNIQUE,\n" +
                "    email VARCHAR(100) NOT NULL UNIQUE,\n" +
                "    senha VARCHAR(100) NOT NULL,\n" +
                "    role ENUM('gerente', 'garcom') NOT NULL\n" +
                ");";

        Statement st = criarConexao().createStatement();
        st.execute(cliente);
        st.execute(mesa);
        st.execute(reserva);
        st.execute(cardapio);
        st.execute(comanda);
        st.execute(Usuario);
    }
}
