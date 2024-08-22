package Controllers;


import java.util.ArrayList;
import java.util.Optional;

import Classes.Book;
import DB.DataBase;
import Models.Books;

public class teste {
    public static void main(String[] args) {
        DataBase db = new DataBase();
        Books books = new Books();
        ArrayList<Object> values = new ArrayList<>();
        byte[] example = null;
                
        values.add("Ana");
        values.add( "Código Limpasddfj");
        values.add( "Redes");
        values.add( 25);
        values.add(example);
        values.add( "Especial");



        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("titulo = "+ "'"+"Código Limpo"+"'");
        
        books.update(db, values, Optional.of(conditions));
    }
}
