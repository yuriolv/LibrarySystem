package Models;

import Classes.RentBook;
import DB.DataBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

public class Rents {  

    public boolean create(RentBook rent, DataBase db){
        try {
            String insert = "Insert into aluguel(matricula, id_livro, tipo_usuario , data_aluguel, data_devolucao) values (?, ?, ?, ?, ?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);

            values.add(rent.getMatricula());
            values.add(rent.getTitulo());
            values.add(rent.getTipo());
            values.add(rent.getDateRent());
            values.add(rent.getDateDevolution());

            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            return false;
    
        }
    }

    public ArrayList<RentBook> read(DataBase db, Optional<ArrayList<String>> conditions){
        ArrayList<RentBook> rents = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("SELECT * FROM aluguel");
                result = String.join(" ", select);
            }else{
                select.add("SELECT * FROM aluguel WHERE");
                String command = String.join(" OR ",conditions.get());
                select.add(command);
                result = String.join(" ", select);
            }
            ResultSet rs = db.requestSQL(result);

            while (rs.next()) {
                String matricula =  Integer.toString(rs.getInt("matricula"));
                RentBook rent = new RentBook(matricula, rs.getString("titulo_livro"), rs.getString("tipo_usuario"), rs.getString("data_aluguel"), rs.getString("data_devolucao"));
                rents.add(rent);
            }

            return rents;
        } catch (Exception e) {
            System.out.println(e);
            return rents;
        }
    }

    public boolean update(DataBase db,ArrayList<Object> values,Optional<ArrayList<String>> conditions){
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(!conditions.isEmpty()){
                select.add("UPDATE aluguel SET matricula = ?,titulo_livro = ?,tipo_usuario = ?,data_aluguel = ?,data_devolucao = ? WHERE");
                String condition = String.join(" AND ", conditions.get());
                select.add(condition);
            }else{
                select.add("UPDATE aluguel SET matricula = ?,titulo_livro = ?,tipo_usuario = ?,data_aluguel = ?,data_devolucao = ?");
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
        command.add("DELETE FROM aluguel WHERE");
        String condition = String.join(" AND ", conditions);
        command.add(condition);

        String delete = String.join(" ", command);
        Boolean rs = db.modifySQL(delete, Optional.empty());

        return rs;
    }
}


