package Controllers;

import java.util.ArrayList;
import java.util.Optional;

import Classes.User;
import DB.DataBase;
import Models.Users;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    public void setLabels(User user){
        nomeLabel.setText(user.getNome());
        matriculaLabel.setText(user.getMatricula());
        tipoLabel.setText(user.getTipo());
        senhaLabel.setText(user.getSenha());
    }

    @FXML
    public void alterarSenha(MouseEvent event){
        ArrayList<Object> valuesToUpdate = new ArrayList<>();
        ArrayList<String> conditions_str = new ArrayList<>();
        Optional<ArrayList<String>> conditions = Optional.of(conditions_str);
        String matricula;

        responseLabel.setText("");
        String novaSenha = passwordTField.getText();
        String confirmarSenha = validationTField.getText();
        users = crud.read(db, Optional.empty());
        int i = getIndexUser();

        if(novaSenha.equals(confirmarSenha)){
            user.setSenha(confirmarSenha);
            users.set(i, user);

            valuesToUpdate.add(user.getSenha());
            matricula = users.get(i).getMatricula() ;
            conditions_str.add(String.format("matricula = '%s'", matricula));
            crud.update(db, valuesToUpdate, conditions);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("SUCESSO");
            alert.setHeaderText(null);
            alert.setContentText("Senha alterada com sucesso!");

            alert.showAndWait();

            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();

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
