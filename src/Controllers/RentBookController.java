package Controllers;

import java.io.IOException;

import Classes.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
        root = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
   // @FXML
    void changePageBooKk(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../Views/book.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

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