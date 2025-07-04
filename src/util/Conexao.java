package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa";
    private static final String USUARIO = "root";
    private static final String SENHA = "12345";

    public static Connection conectar() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException erro) {
            System.out.println("Assinatura do driver não confere " + erro);
            System.exit(0);
        }

        try {
            con = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException erro) {
            System.out.println("Problemas na conexão com o banco de dados " + erro);
        }
        return con;

    }
}