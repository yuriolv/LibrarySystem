package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Classes.Livro;
import Classes.RentBook;
import Classes.User;
import Models.Livros;
import Models.Rents;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RentBookController {
    
    @FXML
    private Label labelSair;
    @FXML
    private Label matriculaLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label assuntoLabel;
    @FXML
    private Label autorLabel;
    @FXML
    private Label bookNameLabel;
    @FXML
    private ImageView capaImage;


    private User user;
    private Livro livro;
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
    public void rentBook(MouseEvent event) throws IOException{
        if(livro.getQtdEstoque()==0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Livro fora de estoque");
            alert.showAndWait();
            return;

        }

        Rents crudRent = new Rents();
        crud = new Livros();
        livros = new ArrayList<Livro>(); 
        int i = 0;
        crud.read(livros);
        
        for (Livro livro : livros) {
            if(livro.getTitulo().equals(livro.getTitulo())) {
                i = livros.indexOf(livro);
                break;
            }
        }

        rentBookClass = new RentBook(user.getMatricula(), livro.getTitulo());
        rentBookClass.setDateRent();

        crudRent.create(rentBookClass);

        livro.setQtdEstoque(livro.getQtdEstoque() - 1);

        livros.set(i, livro);
        crud.update(livros);
        
        changePageBooks(event);

    }

     public void setData(User user, Livro selectedLivro){
        this.user=user;
        this.livro = selectedLivro;
    }

    public void setLabels(User user, Livro livro) throws FileNotFoundException{
        matriculaLabel.setText(user.getMatricula());  
        nomeLabel.setText(user.getNome());
        bookNameLabel.setText(livro.getTitulo());
        autorLabel.setText(livro.getAutor());
        assuntoLabel.setText(livro.getAssunto());

        FileInputStream file = new FileInputStream(livro.getImage());
        Image img = new Image(file);

        capaImage.setImage(img);

    }
}
