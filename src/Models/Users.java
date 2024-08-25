package Models;

import Classes.User;
import DB.DataBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

public class Users {
    
    public boolean create(User user, DataBase db) {
        try {
            String insert = "Insert into usuario(matricula, nome, tipo, senha) values (?, ?, ?, ?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);

            values.add(user.getMatricula());
            values.add(user.getNome());
            values.add(user.getTipo());
            values.add(user.getSenha());

            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<User> read(DataBase db, Optional<ArrayList<String>> conditions){
        ArrayList<User> users = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("SELECT * FROM usuario");
                result = String.join(" ", select);
            }else{
                select.add("SELECT * FROM users WHERE");
                String command = String.join(" OR ",conditions.get());
                select.add(command);
                result = String.join(" ", select);
            }
            ResultSet rs = db.requestSQL(result);

            while (rs.next()) {
                String matricula =  Integer.toString(rs.getInt("matricula"));
                User user = new User(matricula, rs.getString("nome"), rs.getString("tipo"), rs.getString("senha"));
                users.add(user);
            }

            return users;
        } catch (Exception e) {
            System.out.println(e);
            return users;
        }
    }

    public boolean update(DataBase db,ArrayList<Object> values,Optional<ArrayList<String>> conditions){
        String command;

        if (values.size() == 1) {
            command = "UPDATE usuario SET senha = ? WHERE";
        } else if (values.size() == 3) {
            command = "UPDATE usuario SET matricula = ?,nome = ?,tipo = ? WHERE";
        }

        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(!conditions.isEmpty()){
                select.add("UPDATE usuario SET matricula = ?,nome = ?,tipo = ?,senha = ? WHERE");
                String condition = String.join(" AND ", conditions.get());
                select.add(condition);
            }else{
                select.add("UPDATE usuario SET matricula = ?,nome = ?,tipo = ?,senha = ?");
            }


            result = String.join(" ", select);

            db.modifySQL(result, Optional.of(values));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(DataBase db, ArrayList<String> conditions){
        ArrayList<String> command = new ArrayList<>();
        command.add("DELETE FROM usuario WHERE");
        String condition = String.join(" AND ", conditions);
        command.add(condition);

        String delete = String.join(" ", command);
        Boolean rs = db.modifySQL(delete, Optional.empty());

        return rs;
    }
}
