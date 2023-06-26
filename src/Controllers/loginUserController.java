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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class loginUserController {
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
    void verifyLoginUser(MouseEvent event) {

        String typeSelected, user, pass;
        ArrayList<User> users = new ArrayList<User>();
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
                crud.read(users);
                
                resetPane();

                for (User usuario : users) {
                    if(usuario.getMatricula().equals(user)
                        && usuario.getSenha().equals(pass)
                        && usuario.getTipo().equals(typeSelected)){                
                    
                            App.changeScene("pageUser");

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
    public void verifyLoginUser2(KeyEvent event){

        if(event.getCode() == KeyCode.ENTER){

            String typeSelected, user, pass;
            ArrayList<User> users = new ArrayList<User>();
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
                    crud.read(users);
                    
                    resetPane();

                    for (User usuario : users) {
                        if(usuario.getMatricula().equals(user)
                            && usuario.getSenha().equals(pass)
                            && usuario.getTipo().equals(typeSelected)){                
                        
                                App.changeScene("pageUser");

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
    public void changePageHome(MouseEvent e){
        resetPane();
        App.changeScene("pageHome");
    }

    public void resetPane(){
        passPasswordField.setText("");
        userTextField.setText("");
        responseLabel.setText("");

        if(typeGroup.getSelectedToggle() == null)
            return;

        typeGroup.getSelectedToggle().setSelected(false);


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
}
