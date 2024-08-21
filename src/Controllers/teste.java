package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Classes.Book;
import DB.DataBase;
import Models.Books;

public class teste {
    public static void main(String[] args) {
        DataBase db = new DataBase();
        db.initialize();
        Books b = new Books();
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("titulo ='Código Limpo'");
        conditions.add("autor = 'Robert'");
        try {
           ArrayList<Book> result = b.read(db, Optional.empty());
            
            if (result.size()>0) {
                for (Book book : result) {
                   System.out.println(book.getTitulo());;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}
