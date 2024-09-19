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
        int editora = livro.getEditora();
        
        

        try {
            String insert = "Insert into livro(autor, titulo, assunto, qtd_estoque,capa_livro, colecao, id_editora) values (?,?,?,?,?,?,?)";
            ArrayList<Object> values = new ArrayList<>();
            Optional<ArrayList<Object>> arrValues = Optional.of(values);
            values.add(autor);
            values.add(titulo);
            values.add(assunto);
            values.add(qtd_estoque);
            values.add(capa_livro);
            values.add(colecao);
            values.add(editora);

            Boolean result = db.modifySQL(insert, arrValues);
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Book> read(DataBase db, Optional<ArrayList<String>> conditions, Optional<String> comando_logico){
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("SELECT * FROM livro");
                //select.add("select autor, titulo, assunto, qtd_estoque, capa_livro, colecao, editora.nome as editora from livro join editora on livro.id_editora=editora.id_editora");
            }else{
                select.add("SELECT * FROM livro WHERE");
                String command = String.join(comando_logico.get(),conditions.get());
                select.add(command);
            }
            result = String.join(" ", select);
            System.out.println(result);
            ResultSet rs = db.requestSQL(result);

            while (rs.next()) {
                Book book = new Book(rs.getInt("id_livro"),rs.getString("autor"), 
                 rs.getString("titulo"), rs.getString("assunto"), rs.getInt("qtd_estoque"),
                 rs.getString("colecao"), rs.getBytes("capa_livro"), rs.getInt("id_editora"));
                books.add(book);
            }

            return books;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean update(DataBase db,Book livro,Optional<ArrayList<String>> conditions){
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        ArrayList<Object> values = new ArrayList<>();
        Optional<ArrayList<Object>> arrValues = Optional.of(values);
            values.add(livro.getAutor());
            values.add(livro.getTitulo());
            values.add(livro.getAssunto());
            values.add(livro.getQtdEstoque());
            values.add(livro.getImage());
            values.add(livro.getColeção());
            values.add(livro.getEditora());
        try {
            if(!conditions.isEmpty()){
                select.add("UPDATE livro SET autor = ?,titulo = ?,assunto = ?,qtd_estoque = ?,capa_livro = ?,colecao = ?, id_editora = ? WHERE");
                String condition = String.join(" AND ", conditions.get());
                select.add(condition);
            }else{
                select.add("UPDATE livro SET autor = ?,titulo = ?,assunto = ?,qtd_estoque = ?,capa_livro = ?,colecao = ?, id_editora = ?");
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
        command.add("DELETE FROM livro WHERE");
        String condition = String.join(" AND ", conditions);
        command.add(condition);

        String delete = String.join(" ", command);


        Boolean rs = db.modifySQL(delete, Optional.empty());

        return rs;
        
    }

    
}



