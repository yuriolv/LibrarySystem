package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class loginAdminController{
    @FXML
    private Label responseLabel;
    
    @FXML 
    private PasswordField password;
    


    @FXML
    public void changePageHome(MouseEvent e){
        responseLabel.setText("");
        password.setText("");
        App.changeScene("pageHome");
    }

    @FXML 
    public void verifyLoginAdmin(MouseEvent e){
        String passwordInput = password.getText().toString();

        if(passwordInput.equals("uece2023")){
            responseLabel.setText("");
            password.setText("");
            //App.changeScene("pageAdmin");
        }else {
            responseLabel.setText("SENHA INCORRETA! TENTE NOVAMENTE");
            password.setText("");
        }

        
    }

    @FXML 
    public final void clearLabel(KeyEvent e){
        responseLabel.setText("");
    }
}
