package Models;

import Classes.Publisher;
import DB.DataBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

public class Publishers {
    
    public boolean create(Publisher editora, DataBase db) {
        String nome = editora.getNome();
        String cnpj = editora.getCnpj();
        String telefone = editora.getTelefone();        
        
        try {
            String insert = "Insert into editora(nome, cnpj, telefone) values (?,?,?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);
            values.add(nome);
            values.add(cnpj);
            values.add(telefone);

            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            return false;
        }
    }

     public ArrayList<Publisher> read(DataBase db, Optional<ArrayList<String>> conditions, Optional<String> comando_logico){
        ArrayList<Publisher> editoras = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("SELECT * FROM editora");
                result = String.join(" ", select);
            }else{
                select.add("SELECT * FROM editora WHERE");
                String command = String.join(comando_logico.get(),conditions.get());
                select.add(command);
                result = String.join(" ", select);
            }
            System.out.println(result);
            ResultSet rs = db.requestSQL(result);

            while (rs.next()) {
                Publisher editora = new Publisher(rs.getString("nome"), rs.getString("cnpj"), rs.getString("telefone"));
                editoras.add(editora);
            }

            return editoras;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean update(DataBase db, Publisher editora, Optional<ArrayList<String>> conditions){
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        ArrayList<Object> values = new ArrayList<>();
        Optional<ArrayList<Object>> arrValues = Optional.of(values);

        values.add(editora.getNome());
        values.add(editora.getCnpj());
        values.add(editora.getTelefone());

        try {
            if(!conditions.isEmpty()){
                select.add("UPDATE editora SET nome = ?,cnpj = ?,telefone = ? WHERE");
                String condition = String.join(" AND ", conditions.get());
                select.add(condition);
            }else{
                select.add("UPDATE editora SET nome = ?,cnpj = ?,telefone = ?");
            }

            result = String.join(" ", select);

            db.modifySQL(result,arrValues);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public boolean delete(DataBase db, ArrayList<String> conditions){
        ArrayList<String> command = new ArrayList<>();
        command.add("DELETE FROM editora WHERE");
        String condition = String.join(" AND ", conditions);
        command.add(condition);

        String delete = String.join(" ", command);


        Boolean rs = db.modifySQL(delete, Optional.empty());

        return rs;
        
    }
}
