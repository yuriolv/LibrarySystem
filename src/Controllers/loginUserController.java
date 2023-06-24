package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class loginUserController {
    @FXML
    private RadioButton docente;
    @FXML 
    private RadioButton discente;
    @FXML 
    private TextField user;


    @FXML 
    public void handleDocente(MouseEvent e){
        boolean selectDocente = docente.isSelected();
        
        if(selectDocente){
            discente.setSelected(false);
        }

    }

    @FXML 
    public void handleDiscente(MouseEvent e){
        boolean selectDiscente = discente.isSelected();

        if(selectDiscente){
            docente.setSelected(false);
        }

    }

    @FXML 
    public void verifyLoginUser(){
        String typeSelected = getTypeUser();
        String userField = user.getText().toString();
        
    }

    public String getTypeUser(){
        boolean selectDocente = docente.isSelected();
        boolean selectDiscente = discente.isSelected();

        if(selectDiscente)
            return "discente";

        else if(selectDocente)
            return "docente";

            return "";
    }
}
