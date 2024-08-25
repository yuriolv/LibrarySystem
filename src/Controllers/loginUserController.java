package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Classes.User;
import DB.DataBase;
import Models.Users;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class loginUserController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    private PasswordField passPasswordField;
    @FXML
    private ToggleGroup typeGroup;
    @FXML
    private TextField userTextField;
    @FXML
    private Label responseLabel;
    @FXML
    private Label responseLabel2;

    @FXML
    public void verifyLoginUser(MouseEvent event) throws IOException {

        String typeSelected, user, pass;
        Users crud = new Users();
        RadioButton radio = (RadioButton) typeGroup.getSelectedToggle();
        
        try{
            
            if(radio == null){
                responseLabel2.setText("PREENCHA TODOS OS CAMPOS!");
                resetPane();
            } else {

                typeSelected = radio.getText();
            
                user = userTextField.getText();
                pass = passPasswordField.getText();

                ArrayList<User> users = crud.read(db, Optional.empty());
                
                resetPane();

                for (User usuario : users) {
                    if(usuario.getMatricula().equals(user)
                        && usuario.getSenha().equals(pass)
                        && usuario.getTipo().equals(typeSelected)){                
                    
                           changePageUser(event, usuario);

                    } else {
                        responseLabel.setText("MATRICULA OU SENHA INVÁLIDAS!");
                    }
                }

            
            }            
        } catch (NullPointerException e) {
            System.out.println(e);

        }

    }

    @FXML
    public void verifyLoginUser2(KeyEvent event) throws IOException{

        if(event.getCode() == KeyCode.ENTER){

            String typeSelected, user, pass;
            ArrayList<User> users;

            Users crud = new Users();   
            RadioButton radio = (RadioButton) typeGroup.getSelectedToggle();
        
            try{
                
                if(radio == null){
                    responseLabel2.setText("PREENCHA TODOS OS CAMPOS!");
                    resetPane();
                } else {

                    typeSelected = radio.getText();
                
                    user = userTextField.getText();
                    pass = passPasswordField.getText();
                    users = crud.read(db, Optional.empty());
                    
                    resetPane();

                    for (User usuario : users) {
                        if(usuario.getMatricula().equals(user)
                            && usuario.getSenha().equals(pass)
                            && usuario.getTipo().equals(typeSelected)){                
                        
                                changePageUser(event, usuario);

                        } else {
                            responseLabel.setText("MATRICULA OU SENHA INVÁLIDAS!");
                        }
                    }

                
                }            
            } catch (NullPointerException e) {
                System.out.println(e);

            }
        }
    }

    @FXML
    public void changePageHome(MouseEvent event) throws IOException{
        resetPane();
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

    public void changePageUser(Event event, User user) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/User.fxml"));

        root = loader.load();

        UserController userController = loader.getController();

        userController.initializeDB(db);
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
    public final void clearLabelIncorrect(KeyEvent e){
        responseLabel.setText("");
        responseLabel2.setText("");
    }

    @FXML 
    public final void clearLabelIncomplete(MouseEvent e){
        responseLabel.setText("");
        responseLabel2.setText("");
    }

    public void resetPane(){
        passPasswordField.setText("");
        userTextField.setText("");
        responseLabel.setText("");

        if(typeGroup.getSelectedToggle() == null)
            return;

        typeGroup.getSelectedToggle().setSelected(false);


    }
}
