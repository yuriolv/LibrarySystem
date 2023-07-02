package Controllers;

import java.io.IOException;

import Classes.Livro;
import Classes.User;
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

public class RentBookController{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label labelSair;
    @FXML
    private Label matriculaLabel;

    private User user;
    private Livro selectedLivro;

    @FXML
    void changePageUser(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../Views/User.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

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

        BookController bookController = loader.getController();

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


}