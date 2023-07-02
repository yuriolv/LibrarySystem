package Controllers;

import java.util.ArrayList;

import Classes.Livro;
import Classes.RentBook;
import Classes.User;
import Models.Livros;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RentBookController {
    @FXML
    private Label labelSair;
    @FXML
    private Label matriculaLabel;

    private User user;
    private Livro selectedLivro;
    private RentBook rentBookClass;
    private Livros crud;

    private ArrayList<Livro> livros;
    
    @FXML
    public void rentBook(MouseEvent event){ 
        int i = 0;
        crud.read(livros);
        
        for (Livro livro : livros) {
            if(livro.getTitulo().equals(selectedLivro.getTitulo())) {
                i = livros.indexOf(livro);
                break;
            }
        }

        rentBookClass = new RentBook(user.getMatricula(), selectedLivro.getTitulo());
        rentBookClass.setDateRent();
        rentBookClass.setDateDevolution();

        //crudRentBook.create(rentBookClass.toString());

        selectedLivro.setQtdEstoque(selectedLivro.getQtdEstoque() - 1);

        livros.set(i, selectedLivro);
        crud.update(livros);
        //change page rentBooks

    }

     public void setData(User user, Livro selectedLivro){
        this.user=user;
        this.selectedLivro = selectedLivro;
    }

    public void setLabels(User user){
        matriculaLabel.setText(user.getMatricula());    
    }
}
