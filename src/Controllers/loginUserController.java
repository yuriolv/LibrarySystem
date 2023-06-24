package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class loginUserController {
    @FXML
    private ToggleGroup typeGroup;
    @FXML 
    private TextField user;
   /*  @FXML
    private Label responseLabel; */
    @FXML
    private PasswordField password;


    @FXML 
    public void verifyLoginUser(){
        String typeSelected = typeGroup.getSelectedToggle().toString();
        String userField = user.getText().toString();
        
    }

    @FXML
    public void changePageHome(MouseEvent e){
        //responseLabel.setText("");
        password.setText("");
        App.changeScene("pageHome");
    }

}
