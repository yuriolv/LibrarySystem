package Models;

import Classes.Book;
import DB.DataBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

public class Books {
    
    public boolean create(Book livro, DataBase db){
        String autor = livro.getAutor();
        String titulo = livro.getTitulo();
        String assunto = livro.getAssunto();
        int qtd_estoque = livro.getQtdEstoque();
        String colecao = livro.getColeção();
        byte[] capa_livro = livro.getImage();
        
        
        try {
            String insert = "Insert into livro(autor, titulo, assunto, qtd_estoque,capa_livro, colecao) values (?,?,?,?,?,?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);
            values.add(autor);
            values.add(titulo);
            values.add(assunto);
            values.add(qtd_estoque);
            values.add(capa_livro);
            values.add(colecao);

            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Book> read(DataBase db, Optional<ArrayList<String>> conditions){
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("SELECT * FROM livro");
                result = String.join(" ", select);
            }else{
                select.add("SELECT * FROM livro WHERE");
                String command = String.join(" OR ",conditions.get());
                select.add(command);
                result = String.join(" ", select);
            }
            ResultSet rs = db.requestSQL(result);

            while (rs.next()) {
                Book book = new Book(rs.getString("autor"), rs.getString("titulo"), rs.getString("assunto"), rs.getInt("qtd_estoque"), rs.getString("colecao"), rs.getBytes("capa_livro"));
                books.add(book);
            }

            return books;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean update(DataBase db,ArrayList<Object> values,Optional<ArrayList<String>> conditions){
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(!conditions.isEmpty()){
                System.out.println("entrei");
                select.add("UPDATE livro SET autor = ?,titulo = ?,assunto = ?,qtd_estoque = ?,capa_livro = ?,colecao = ? WHERE");
                String condition = String.join(" AND ", conditions.get());
                select.add(condition);
            }else{
                select.add("UPDATE livro SET autor = ?,titulo = ?,assunto = ?,qtd_estoque = ?,capa_livro = ?,colecao = ?");
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
        command.add("DELETE FROM livro WHERE");
        String condition = String.join(" AND ", conditions);
        command.add(condition);

        String delete = String.join(" ", command);


        Boolean rs = db.modifySQL(delete, Optional.empty());

        return rs;
        
    }

    
}



