package Models;

import Classes.RentBook;
import DB.DataBase;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class Rents {  

    public boolean create(RentBook rent, DataBase db){
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date_rent;


        try {
            String insert = "Insert into aluguel(matricula, id_livro, data_aluguel) values (?, ?, ?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);

            values.add(rent.getMatricula());
            values.add(rent.getId_livro());
            date_rent = LocalDate.parse(rent.getDateRent(), dateTimeFormatter);
            values.add(Date.valueOf(date_rent));

            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            return false;
    
        }
    }

    public ArrayList<RentBook> read(DataBase db, Optional<ArrayList<String>> conditions){
        ArrayList<RentBook> rents = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
        String result = "";
        String titulo_livro = "";
        String tipo_usuario = "";
        String data_aluguel = "";
        String data_devolucao = "";

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
                int id_livro = rs.getInt("id_livro");
                ResultSet rs_livro = db.requestSQL("SELECT titulo FROM livro WHERE id_livro = " + id_livro);
                rs_livro.next();
                titulo_livro = rs_livro.getString("titulo");

                String matricula = "\'" + rs.getString("matricula") + "\'";
                ResultSet rs_usuario = db.requestSQL("SELECT tipo FROM usuario WHERE matricula = " + matricula);
                rs_usuario.next();
                tipo_usuario = rs_usuario.getString("tipo");

                data_aluguel = df.format(rs.getDate("data_aluguel"));

                if (rs.getDate("data_devolucao") == null) {
                    data_devolucao = null;
                } else {
                    data_devolucao = df.format(rs.getDate("data_devolucao"));
                }

                RentBook rent = new RentBook(matricula, id_livro, tipo_usuario, data_aluguel, data_devolucao);
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
        LocalDate data_aluguel;
        String result = "";
        String data_formatada = "";
        ResultSet rs;
        try {
            rs = db.requestSQL("SELECT id_livro FROM livro WHERE titulo = " + conditions.get().get(0));
            rs.next();
            Integer id_livro = rs.getInt("id_livro");
            conditions.get().set(0, "id_livro = " + id_livro);

            data_aluguel = LocalDate.parse(conditions.get().get(1), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            data_formatada = data_aluguel.format(DateTimeFormatter.ofPattern("YYYYMMDD"));
            conditions.get().set(1, "data_aluguel = " + data_formatada);

            if(!conditions.isEmpty()){
                select.add("UPDATE aluguel data_devolucao = ? WHERE");
                String condition = String.join(" AND ", conditions.get());
                select.add(condition);
            }else{
                select.add("UPDATE aluguel data_devolucao = ?");
            }

            result = String.join(" ", select);

            db.modifySQL(result, Optional.of(values));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    /*
        
    public boolean delete(DataBase db, ArrayList<String> conditions){
        ArrayList<String> command = new ArrayList<>();
        command.add("DELETE FROM aluguel WHERE");
        String condition = String.join(" AND ", conditions);
        command.add(condition);

        String delete = String.join(" ", command);
        Boolean rs = db.modifySQL(delete, Optional.empty());

        return rs;
    }
    */
}


