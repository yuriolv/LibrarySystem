package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class loginUserController {
    @FXML
    private ToggleGroup typeGroup;
    @FXML 
    private TextField user;


    @FXML 
    public void verifyLoginUser(){
        String typeSelected = typeGroup.getSelectedToggle().toString();
        String userField = user.getText().toString();
        
    }

}
