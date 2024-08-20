package DB;

import java.sql.*;

public class DataBase {
    private static String url;
    private static String user;
    private static String password;

    public DataBase(){
        this.url = "jdbc:postgresql://localhost:1245/Library";
        this.user = "postgres";
        this.password = "1245";
    }
    public static void initialize(){
        
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            
            System.out.println("Conectado com sucesso!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void requestSQL(String command) {
        String insertSQL = command;
        
        try {
            Connection connection;
            Statement stmt = connection.createStatement();
            stmt.executeQuery(insertSQL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
