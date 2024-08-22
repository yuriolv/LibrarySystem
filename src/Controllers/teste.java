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
        
        books.read(db, Optional.empty());
    }
}
