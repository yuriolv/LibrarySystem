package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Optional;

import Classes.User;
import DB.DataBase;
import Models.Users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminUsersController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> matriculaColumn;
    @FXML
    private TableColumn<User, String> nomeColumn;
    @FXML
    private TableColumn<User, String> tipoColumn;
    @FXML
    private TableColumn<User, String> telefoneColumn;
    @FXML
    private TextField matriculaTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField tipoTextField;
    @FXML
    private TextField telefoneTextField;
    @FXML
    private TextField filtroTextField;

    private ArrayList<User> users;
    private ObservableList<User> usersObs;

    

    public void init() {
        Users crud = new Users();
        users = new ArrayList<>();
        
        users = crud.read(db, Optional.empty());
        usersObs = FXCollections.observableArrayList(users);

        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("Tipo"));

        
        tableUsers.setItems(usersObs);
    }

    @FXML 
    public void editarUser(MouseEvent event){
        ArrayList<String> condition_str;
        Optional<ArrayList<String>> conditions = Optional.of(condition_str);
        ArrayList<Object> valuesToUpdate= new ArrayList<>();
        Users crud = new Users();
        String nome, tipo, matricula;
        User user;

        int i = tableUsers.getSelectionModel().getSelectedIndex();
        users = crud.read(db, Optional.empty());

        try {
            matricula = matriculaTextField.getText();
            tipo = tipoTextField.getText();
            nome = nomeTextField.getText();
            valuesToUpdate.add("matricula");
            valuesToUpdate.add("tipo");
            valuesToUpdate.add("nome");
                        
            user = users.get(i);

            user.setMatricula(matricula);
            user.setNome(nome);
            user.setTipo(tipo);

            users.set(i, user);
            usersObs.set(i, user);
            
            condition_str.add(String.format("matricula = %s", matricula));
            crud.update(db, valuesToUpdate, conditions);
            resetTextFields();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML 
    public void adicionarUser(MouseEvent event){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

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
            crud.create(user, db);


            resetTextFields();

        } catch (Exception e) {

            alert.setContentText("Ocorreu um erro");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    public void removerUser(MouseEvent event){
        ArrayList<String> conditions = new ArrayList<>();
        Users crud = new Users();

        int i = tableUsers.getSelectionModel().getSelectedIndex();
        
        if(i<0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.setContentText("Selecione uma linha para remover");
            alert.showAndWait();
            return;
        }

        usersObs.remove(i);

        conditions.add(String.format("matricula = %s", users.get(i).getMatricula()));
        crud.delete(db, conditions); 
        
        resetTextFields();
    }

    @FXML
    public void pesquisarUser(MouseEvent event){
        Users crud = new Users();
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        
        
        users = crud.read(db, Optional.empty()); 
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
            
            
            users = crud.read(db, Optional.empty()); 
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
    public void changePageAdmin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Admin.fxml"));
        root = loader.load();

        AdminController adminController = loader.getController();
        adminController.initializeDB(db);

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void changePageHome(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Home.fxml"));
        root = loader.load();

        HomeController homeController = loader.getController();
        homeController.initializeDB(db);
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show();
        
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
