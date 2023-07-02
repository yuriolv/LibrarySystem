package Controllers;

import java.util.ArrayList;

import Classes.User;
import Models.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class EditarUserController {
    @FXML 
    private Label nomeLabel;

    @FXML 
    private Label matriculaLabel;

    @FXML 
    private Label tipoLabel;

    @FXML
    private Label senhaLabel;

    @FXML
    private PasswordField passwordTField;

    @FXML
    private PasswordField validationTField;

    @FXML
    private Label responseLabel;

    private User user;

    private ArrayList<User> users = new ArrayList<>();

    private Users crud = new Users();

    public void setLabels(User user){
        nomeLabel.setText(user.getNome());
        matriculaLabel.setText(user.getMatricula());
        tipoLabel.setText(user.getTipo());
        senhaLabel.setText(user.getSenha());
    }

    @FXML
    public void alterarSenha(MouseEvent event){
        responseLabel.setText("");
        String novaSenha = passwordTField.getText();
        String confirmarSenha = validationTField.getText();
        crud.read(users);
        int i = getIndexUser();

        if(novaSenha.equals(confirmarSenha)){
            user.setSenha(confirmarSenha);
            users.set(i, user);
            crud.update(users);
        }else{
            responseLabel.setText("Senhas digitadas não conferem");
        }
    }

    public int getIndexUser(){
        int i = 0;

        for (User selectedUser : users) {
            if(selectedUser.getMatricula().equals(user.getMatricula())){
                i = users.indexOf(selectedUser);
                return i;
            }
        }
        return i;
    }

    public void setData(User user){
        this.user = user;
    }
}
