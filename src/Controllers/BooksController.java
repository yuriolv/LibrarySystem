package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Classes.Livro;
import Classes.User;
import Models.Livros;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class BooksController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label labelSair;
    @FXML
    private Label matriculaLabel;
    @FXML
    private FlowPane flowPane;

    private User user;
    private Livro selectedLivro;

    private ArrayList<Livro> livros;

    @FXML
    void changePageUser(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/User.fxml"));

        root = loader.load();

        UserController userController = loader.getController();

        userController.setData(user);
        userController.setLabels(user);

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
    void changePageHome(MouseEvent event) throws IOException{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("../Views/Home.fxml"));

        root = loader.load();

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
   // @FXML
    void changePageBook(MouseEvent event) throws IOException{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("../Views/Home.fxml"));
        root = loader.load();

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        RentBookController bookController = loader.getController();

        bookController.setData(user, selectedLivro );
        bookController.setLabels(user);

        stage.setScene(scene);
        stage.show();
    }


    public void setData(User user){
        this.user=user;
    }

    public void setLabels(User user){
        matriculaLabel.setText(user.getMatricula());    
    }

    public AnchorPane createAnchorPane(String bookName, String bookAuthor) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(179);
        anchorPane.setPrefHeight(241);

        Label name = new Label(bookName);
        Label author = new Label(bookAuthor);

        anchorPane.getChildren().addAll(name, author);
        return anchorPane;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
        Livros crud = new Livros();

        livros = new ArrayList<Livro>();
        crud.read(livros);

        for (Livro livro : livros) {
            AnchorPane bookCard = createAnchorPane(livro.getTitulo(), livro.getAutor());
            flowPane.getChildren().add(bookCard);
        }
    }

}