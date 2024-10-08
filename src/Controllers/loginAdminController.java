package Controllers;

import java.io.IOException;

import DB.DataBase;
import Utils.HashPass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class loginAdminController{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    private Label responseLabel;
    
    @FXML 
    private PasswordField password;
    


    @FXML
    public void changePageHome(MouseEvent event) throws IOException{
        responseLabel.setText("");
        password.setText("");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Home.fxml"));
        root = loader.load();

        HomeController homeController = loader.getController();
        homeController.initializeDB(db);
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
    public void verifyLoginAdmin(ActionEvent event) throws IOException{
        String passwordInput = password.getText().toString();

        if(HashPass.verifyPass("$2a$12$fpfUaB3RMywfWUtHTwSEFOTRuY2QNXlUKeHAcd56neNvGN/8bhKXu", passwordInput)){
            changePageBooks(event);
        }else {
            responseLabel.setText("SENHA INCORRETA! TENTE NOVAMENTE");
            password.setText("");
        }
    }

    @FXML
    public void verifyLoginAdmin2(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
            String passwordInput = password.getText().toString();
    
            if(HashPass.verifyPass("$2a$12$fpfUaB3RMywfWUtHTwSEFOTRuY2QNXlUKeHAcd56neNvGN/8bhKXu", passwordInput)){
                changePageBooks(event);

            }else {
                responseLabel.setText("SENHA INCORRETA! TENTE NOVAMENTE");
                password.setText("");
            }
        }
    }
    @FXML 
    public final void clearLabel(KeyEvent e){
        responseLabel.setText("");
    }

    
    public void changePageBooks(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AdminLivros.fxml"));
        root = loader.load();
        
        AdminLivrosController adminLivrosController = loader.getController();
        adminLivrosController.initializeDB(db);

        adminLivrosController.init();

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
