package Controllers;

import java.io.IOException;

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

public class UserController{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label matriculaLabel;

    @FXML
    private Label nomeLabel;

    private User user;

    @FXML
    public void changePageHome(MouseEvent event) throws IOException {
       
    }

    @FXML
    public void changePageRentBook(MouseEvent event) throws IOException {
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
    public void changePageDevolution(MouseEvent event) {
       // App.changeScene("pageAdminUsers");
    }

    @FXML
    public void changeGenerateReport(MouseEvent event) {
       // App.changeScene("pageAdminUsers");
    }

   public void setData(User user){
       this.user=user;
   } 
   public void setLabels(User user){
        matriculaLabel.setText(user.getMatricula());
        nomeLabel.setText(user.getNome()); 
   }


}

