package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import Classes.Livro;
import Classes.RentBook;
import Classes.User;
import Models.Livros;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void changePageHome(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    public void changePageBooks(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Books.fxml"));

        root = loader.load();

        BooksController rentController = loader.getController();

        rentController.setData(user);
        rentController.setLabels(user);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show(); 
    }

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
