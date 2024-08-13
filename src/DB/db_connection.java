package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class db_connection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:1245/Library";
        String user = "postgres";
        String password = "1245";
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(url, user, password);
            String insertSQL = "UPDATE usuarios SET nome = 'João Silva', email = 'joao@uece.br'";

            Statement stmt = conn.createStatement();
            stmt.executeQuery(insertSQL);

            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
}
