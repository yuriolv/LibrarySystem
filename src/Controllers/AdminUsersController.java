package Controllers;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Optional;

import Classes.User;
import DB.DataBase;
import Models.Users;
import Utils.HashPass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
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
    private TextField matriculaTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField tipoTextField;
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
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

        ArrayList<String> condition_str = new ArrayList<>();
        Optional<ArrayList<String>> conditions = Optional.of(condition_str);
        ArrayList<Object> valuesToUpdate= new ArrayList<>();

        Users crud = new Users();
        String nome, tipo, matricula;
        int i = tableUsers.getSelectionModel().getSelectedIndex();
        User user_antes = tableUsers.getItems().get(i);


        
        users = crud.read(db, Optional.empty());

        try {
            matricula = matriculaTextField.getText();
            tipo = tipoTextField.getText();
            nome = nomeTextField.getText();

            if (matricula.equals("") || tipo .equals("") || nome.equals("")){
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
                return; 
            }

            String mat_antes = user_antes.getMatricula();
            for ( User user: usersObs){
                if (user.getMatricula().equals(matricula) && !mat_antes.equals(matricula)) {
                    alert.setContentText("Matricula já existente");
                    alert.showAndWait();
                    return; 
                }
            }
                

            valuesToUpdate.add(matricula);
            valuesToUpdate.add(nome);
            valuesToUpdate.add(tipo);
                        
            User user = users.get(i);

            user.setMatricula(matricula);
            user.setNome(nome);
            user.setTipo(tipo);

            condition_str.add(String.format("matricula = '%s'", user_antes.getMatricula()));
            crud.update(db, valuesToUpdate, conditions);

            users.set(i, user);
            usersObs.set(i, user);
            
            resetTextFields();

        } catch (Exception e) {
            alert.setContentText("Ocorreu um erro");
            alert.showAndWait();
            return;
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


            if (matricula.equals("") || tipo .equals("") || nome.equals("")){
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
                return; 
            }

            for ( User user: usersObs){
                if (user.getMatricula().equals(matricula)) {
                    alert.setContentText("Matricula já existente");
                    alert.showAndWait();
                    return; 
                }
            }

                

            String firstname = nomeTextField.getText().split(" ")[0];
            senha = HashPass.generateHash(firstname+"@"+tipo);
            User user = new User(matricula, nome, tipo, senha);

            crud.create(user, db);
            users.add(user);
            usersObs.add(user);


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
        String matricula;

        int i = tableUsers.getSelectionModel().getSelectedIndex();
        
        if(i<0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.setContentText("Selecione uma linha para remover");
            alert.showAndWait();
            return;
        }

        
        matricula = "\'" + users.get(i).getMatricula() + "\'";
        conditions.add(String.format("matricula = %s", matricula));
        crud.delete(db, conditions); 
        
        usersObs.remove(i);
        resetTextFields();
    }

    @FXML
    public void pesquisarUser(MouseEvent event){
        String filtro = filtroTextField.getText();

        usersObs.clear();

        Users crud = new Users();

        ArrayList<String> like = new ArrayList<String>();
        
        like.add("matricula LIKE '%" + filtro + "%'");
        like.add("UPPER(nome) LIKE UPPER('%" + filtro + "%')");
        like.add("UPPER(tipo) LIKE UPPER('%" + filtro + "%')");

        Optional<ArrayList<String>> conditions = Optional.of(like);
        users = crud.read(db, conditions);

        if (users == null) {
            return;
        }
     
        for(User user: users) {
            usersObs.add(user);
        }

        tableUsers.setItems(usersObs);
    }

    @FXML
    void pesquisarUser2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){

            String filtro = filtroTextField.getText();

            usersObs.clear();

            Users crud = new Users();

            ArrayList<String> like = new ArrayList<String>();
            
            like.add("matricula LIKE '%" + filtro + "%'");
            like.add("UPPER(nome) LIKE UPPER('%" + filtro + "%')");
            like.add("UPPER(tipo) LIKE UPPER('%" + filtro + "%')");

            Optional<ArrayList<String>> conditions = Optional.of(like);
            users = crud.read(db, conditions);

            if (users == null) {
                return;
            }
         
            for(User user: users) {
                usersObs.add(user);
            }

            tableUsers.setItems(usersObs);
            
        }

    }

    @FXML
    public void changePageBooks(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AdminLivros.fxml"));
        root = loader.load();
        
        AdminLivrosController adminLivrosController = loader.getController();
        adminLivrosController.initializeDB(db);

        adminLivrosController.init();

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
    public void changePageEditora(MouseEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AdminEditora.fxml"));
        root = loader.load();

        AdminEditoraController adminEditoraController = loader.getController();
        adminEditoraController.initializeDB(db);
        adminEditoraController.init();

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
