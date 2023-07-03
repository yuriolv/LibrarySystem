package Controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

import Classes.Livro;
import Classes.User;
import Models.Livros;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    
    BlurType threePassBox = BlurType.THREE_PASS_BOX;
    private  DropShadow dropShadow = new DropShadow(threePassBox, Color.BLACK, 10, 0, 0, 0);

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
    void changePageBook(MouseEvent event) throws IOException{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("../Views/book.fxml"));
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

        bookController.setData(user, selectedLivro);
        bookController.setLabels(user, selectedLivro);

        stage.setScene(scene);
        stage.show();
    }


    public void setData(User user){
        this.user=user;
    }

    public void setLabels(User user){
        matriculaLabel.setText(user.getMatricula());    
    }

    public AnchorPane createAnchorPane(Livro livro) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(179);
        anchorPane.setPrefHeight(241);
        anchorPane.setStyle("-fx-background-color: white;"+"-fx-background-radius: 10;"+"-fx-cursor:HAND;");
        anchorPane.setEffect(dropShadow);
        
        try {
            FileInputStream file = new FileInputStream(livro.getImage());
            Image img = new Image(file);
            ImageView image = new ImageView(img);

            image.setFitHeight(142);
            image.setFitWidth(97);

            image.setLayoutX(41);
            image.setLayoutY(14);

            Label name = new Label(livro.getTitulo());
            Label author = new Label(livro.getAutor());
            Label subject = new Label(livro.getAssunto());

            
            name.setStyle("-fx-font-weight: bold;"+"-fx-font-size: 12;");
            author.setStyle("-fx-font-style: italic;"+"-fx-font-size: 14;");
            subject.setStyle("-fx-font-size: 14;");

            name.setLayoutX(14);
            name.setLayoutY(167);

            author.setLayoutX(14);
            author.setLayoutY(184);

            subject.setLayoutX(14);
            subject.setLayoutY(204);
    
            anchorPane.getChildren().addAll(name, author, subject, image);
        } catch (Exception e) {
            System.out.println(e);
        }

        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
               try   {
                selectedLivro = livro;
                changePageBook(arg0);
               } catch(Exception e) {
                System.out.println(e);
               }
            }
            
        });
        return anchorPane;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
        Livros crud = new Livros();

        livros = new ArrayList<Livro>();
        crud.read(livros);
        
        for (Livro livro : livros) {
            AnchorPane bookCard = createAnchorPane(livro);
            flowPane.getChildren().add(bookCard);
        }
       
    }

}