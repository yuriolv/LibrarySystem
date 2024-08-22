package DB;

import java.sql.*;
import java.util.*;

public class DataBase {
    private String url;
    private String user;
    private String password;
    public Connection connection;

    public DataBase(){
        this.url = "jdbc:postgresql://localhost:1245/Library";
        this.user = "postgres";
        this.password = "1245";
        try{
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    //Função utilizada para alteração de objetos no SQL
    public Boolean modifySQL(String command,Optional<ArrayList<Object>> values) {
        int result = 0;
        try {
            if(!values.isEmpty()){
                PreparedStatement stmt = connection.prepareStatement(command);
                for (int i=0; i<values.get().size(); i++) {
                    Object obj = values.get().get(i);
                    
                    if(obj instanceof String){
                        stmt.setString(i+1, (String) obj);
                    }else if(obj instanceof Integer){
                        stmt.setInt(i+1, (Integer) obj);
                    }else if(obj instanceof byte[]){
                        stmt.setBytes(i+1, (byte[]) obj);
                    }
                }
                result = stmt.executeUpdate();
            }else {
                Statement stmt = connection.createStatement();
                result = stmt.executeUpdate(command);
            }
            System.out.println(result);
            return result != 0;
         
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    //Função utilizada para 
    public ResultSet requestSQL(String command) {
        
        try(
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(command);) {
            
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
