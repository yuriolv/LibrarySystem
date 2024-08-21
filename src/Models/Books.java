package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

import Classes.Book;
import DB.DataBase;
import java.sql.ResultSet;

public class Books {
    
    public boolean create(Book livro, DataBase db){
        String autor = livro.getAutor();
        String titulo = livro.getTitulo();
        String assunto = livro.getAssunto();
        int qtd_estoque = livro.getQtdEstoque();
        String colecao = livro.getColeção();
        byte[] capa_livro = livro.getImage();
        
        
        try {
            String insert = "Insert into books(autor, titulo, assunto, qtd_estoque,capa_livro, colecao) values (?,?,?,?,?,?)";
            ArrayList<Object> values = new ArrayList<>();
            values.add(autor);
            values.add(titulo);
            values.add(assunto);
            values.add(qtd_estoque);
            values.add(capa_livro);
            values.add(colecao);

            Boolean result = db.modifySQL(insert, values);
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
                select.add("SELECT * FROM books");
                result = String.join(" ", select);
            }else{
                select.add("WHERE");
                for(String command: conditions.get()){
                    select.add(command);
                }
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
            return books;
        }
    }

    public boolean update(DataBase db,Optional<ArrayList<String>> values,Optional<ArrayList<String>> conditions){
        ArrayList<String> select = new ArrayList<>();
        String result = "";
        try {
            if(conditions.isEmpty()){
                select.add("UPDATE books SET");
                for(String command: values.get()){
                    select.add(command);
                }
                select.add("WHERE");
                for(String command: conditions.get()){
                    select.add(command);
                }
                result = select.toString();
            }else if(conditions.isEmpty()){
                return false;
            }

            ResultSet rs = db.requestSQL(result);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
    public boolean delete(int index, ArrayList<Books> livros){
        File file = new File("src/Data/Acervo.txt");
        
        if(file.delete()){
            if(createFile("Acervo")){
                livros.remove(index);

                for (Books livro : livros) {
                    //create(livro);                 
                }
                return true;
            }
        }
        return false; 

    }
    public boolean createFile(String nome){

        File file = new File("src/Data/"+nome+".txt");
            
        try {
            return file.createNewFile();
        } catch (Exception e) {
            return false;
        }
    }

    
}



