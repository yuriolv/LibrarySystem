package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Classes.User;
import Models.Users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    @FXML
    private TextField filtroTextField;

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
        User user;

        crud.read(users);

        try {

            matricula = matriculaTextField.getText();
            tipo = tipoTextField.getText();
            nome = nomeTextField.getText();

            user = users.get(i);

            user.setMatricula(matricula);
            user.setNome(nome);
            user.setTipo(tipo);

           /*  users.set(i, user);
           */
           
           usersObs.set(i, user); 
            crud.update(users);
            resetTextFields();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML 
    public void adicionarUser(MouseEvent event){
        String nome, tipo, matricula, senha;
        Users crud  = new Users();

        try {

            matricula = matriculaTextField.getText();
            tipo = tipoTextField.getText();
            nome = nomeTextField.getText();
            senha = nome+"@"+tipo;
            User user = new User(matricula, nome, tipo, senha);

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
    public void pesquisarUser(MouseEvent event){
        Users crud = new Users();
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        
        
        crud.read(users); 
        if(filtroTextField.getText().equals("")) {
            tableUsers.setItems(usersObs);
        }

        else {
            String filtro = filtroTextField.getText();
            for(User user: users) {
                if(user.getMatricula().equals(filtro) || user.getNome().equals(filtro) || user.getTipo().equals(filtro)) {
                    filteredList.add(user);
                }
            }

            tableUsers.setItems(filteredList);
        }
        resetTextFields();
    }
    @FXML
    void pesquisarUser2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            Users crud = new Users();
            ObservableList<User> filteredList = FXCollections.observableArrayList();
            
            
            crud.read(users); 
            if(filtroTextField.getText().equals("")) {
                tableUsers.setItems(usersObs);
            }
    
            else {
                String filtro = filtroTextField.getText();
                for(User user: users) {
                    if(user.getMatricula().equals(filtro) || user.getNome().equals(filtro) || user.getTipo().equals(filtro)) {
                        filteredList.add(user);
                    }
                }
    
                tableUsers.setItems(filteredList);
            }
            resetTextFields();
        
        }

    }

    @FXML
    public void changePageAdmin(MouseEvent event) {
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
        filtroTextField.setText("");
    }    


}
