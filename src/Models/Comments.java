package Models;


import java.util.ArrayList;
import java.util.Optional;

import Classes.Comment;
import DB.DataBase;
import java.sql.ResultSet;

public class Comments {
    
    public boolean create(DataBase db, Comment comment) {
        try {
            String insert = "INSERT INTO comentario(id_livro, matricula, avaliação) values (?,?,?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);
            
            values.add(comment.getId_livro());
            values.add(comment.getMatricula_usuario());
            values.add(comment.getComentario());


            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Comment> read(DataBase db, Optional<ArrayList<String>> conditions) {
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("SELECT * FROM comentario");
                result = String.join(" ", select);
            }else{
                select.add("SELECT * FROM comentario WHERE");
                String command = String.join(" AND ",conditions.get());
                select.add(command);
                result = String.join(" ", select);
            }
            ResultSet rs = db.requestSQL(result);

            while (rs.next()) {
                Comment comment = new Comment(rs.getInt("id_livro"), rs.getString("matricula"), rs.getString("avaliação"));
                comments.add(comment);
            }

            return comments;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
