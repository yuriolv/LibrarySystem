package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Classes.User;
import Models.Users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminUsersController  implements Initializable{

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, String> matriculaColumn;

    @FXML
    private TableColumn<User, String> nomeColumn;

    @FXML
    private TableColumn<User, String> tipoColumn;

    @FXML
    private TextField matriculaTextField;

    @FXML
    private TextField nomeTextField;
    
    @FXML
    private TextField tipoTextField;

    private TextField pesquisaTextField;

    private ArrayList<User> users;

    private ObservableList<User> usersObs;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Users crud = new Users();
        users = new ArrayList<>();
        
        crud.read(users);
        usersObs = FXCollections.observableArrayList(users);

        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("Tipo"));

        
        tableUsers.setItems(usersObs);
    }

    @FXML 
    public void editarUser(MouseEvent event){
        int i = tableUsers.getSelectionModel().getSelectedIndex();
        Users crud = new Users();
        String nome, tipo, matricula;

        try {

            matricula = matriculaTextField.getText();
            tipo = tipoTextField.getText();
            nome = nomeTextField.getText();

            User user = new User(matricula, nome, tipo);

            users.set(i, user);
            usersObs.set(i, user);

            crud.update(users);
            resetTextFields();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML 
    public void adicionarUser(MouseEvent event){
        String nome, tipo, matricula;
        Users crud  = new Users();

        try {

            matricula = matriculaTextField.getText();
            tipo = tipoTextField.getText();
            nome = nomeTextField.getText();

            User user = new User(matricula, nome, tipo);

            users.add(user);
            usersObs.add(user);
            crud.create(user);


            resetTextFields();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void removerUser(MouseEvent event){
        Users crud = new Users();

        int i = tableUsers.getSelectionModel().getSelectedIndex();
        
        usersObs.remove(i);
        crud.delete(i, users); 
        
        resetTextFields();
    }

    @FXML
    public void pesquisarUsuario(ActionEvent event){
        Users crud = new Users();
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        
        
        crud.read(users); 
        if(pesquisaTextField.getText().equals("")) {
            tableUsers.setItems(usersObs);
        }

        else {
            String filtro = pesquisaTextField.getText();
            for(User user: users) {
                if(user.getMatricula().equals(filtro) || user.getNome().equals(filtro) || user.getTipo().equals(filtro)) {
                    filteredList.add(user);
                }
            }

            tableUsers.setItems(filteredList);
        }
    }

    @FXML
    public void changePageAdmin(ActionEvent event) {
        App.changeScene("pageAdmin");

    }

    @FXML
    public void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
        
    }

    @FXML
    public void getRowData(MouseEvent event){
        int i = tableUsers.getSelectionModel().getSelectedIndex();

        User user = (User) tableUsers.getItems().get(i);

        matriculaTextField.setText(user.getMatricula());
        nomeTextField.setText(user.getNome());
        tipoTextField.setText(user.getTipo());
    }

    public void resetTextFields(){
        matriculaTextField.setText("");
        nomeTextField.setText("");
        tipoTextField.setText("");
    }    


}
