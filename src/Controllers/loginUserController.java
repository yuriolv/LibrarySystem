package Controllers;

import java.util.ArrayList;

import Classes.User;
import Models.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class loginUserController {
    @FXML
    private PasswordField passPasswordField;
    @FXML
    private ToggleGroup typeGroup;
    @FXML
    private TextField userTextField;
    

    @FXML
    void verifyLoginUser(MouseEvent event) {
        String typeSelected, user, pass;
        ArrayList<User> users = new ArrayList<User>();
        Users crud = new Users();
        
        RadioButton radio = (RadioButton) typeGroup.getSelectedToggle();
        typeSelected = radio.getText();
        user = userTextField.getText();
        pass = passPasswordField.getText();
        crud.readUser(users);
        
        resetPane();
        for (User usuario : users) {
            if(usuario.getMatricula().equals(user)
             && usuario.getSenha().equals(pass)
             && usuario.getTipo().equals(typeSelected)){                
                
                App.changeScene("pageMenuUser");
            }
        }




    }


    @FXML
    public void changePageHome(MouseEvent e){
        resetPane();
        App.changeScene("pageHome");
    }

    public void resetPane(){
        passPasswordField.setText("");
        userTextField.setText("");
        typeGroup.getSelectedToggle().setSelected(false);

    }

}
