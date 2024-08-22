package Controllers;

import java.io.IOException;

import DB.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomeController{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }


    @FXML
    public void changeLoginAdmin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/loginAdmin.fxml"));
        root = loader.load();

        loginAdminController loginAdminController = loader.getController();
        loginAdminController.initializeDB(db);

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
    public void changeLoginUser(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/LoginUser.fxml"));
        root = loader.load();

        loginUserController loginUserController = loader.getController();
        loginUserController.initializeDB(db);
        
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


}
