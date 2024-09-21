package Controllers;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Optional;

import Classes.Publisher;
import DB.DataBase;
import Models.Publishers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

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


public class AdminEditoraController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }   
    
    @FXML
    private TableView<Publisher> tableEditora;
    @FXML
    private TableColumn<Publisher, String> nomeColumn;
    @FXML
    private TableColumn<Publisher, String> cnpjColumn;
    @FXML
    private TableColumn<Publisher, String> telefoneColumn;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cnpjTextField;
    @FXML
    private TextField telefoneTextField;
    @FXML
    private TextField filtroTextField;

    private ArrayList<Publisher> editoras;
    private ObservableList<Publisher> editorasObs;

    

    public void init() {
        Publishers crud = new Publishers();

        editoras = crud.read(db, Optional.empty(), Optional.empty());

        editorasObs = FXCollections.observableArrayList(editoras);

        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        cnpjColumn.setCellValueFactory(new PropertyValueFactory<>("Cnpj"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefone"));

        
        tableEditora.setItems(editorasObs);
    }

    @FXML 
    public void editarEditora(MouseEvent event){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

        int i = tableEditora.getSelectionModel().getSelectedIndex();
        Publisher editora_antes = (Publisher) tableEditora.getItems().get(i);

        Publishers crud = new Publishers();
        String nome, cnpj, telefone;
        
        tableEditora.setItems(editorasObs);
   
        nome = nomeTextField.getText();
        cnpj =  cnpjTextField.getText();
        telefone = telefoneTextField.getText();
        
        if(nome.equals("") || cnpj.equals("") || telefone.equals("")){
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return; 
        }
        resetTextFields();
        ArrayList<String> conditions = new ArrayList<>();

        conditions.add(String.format("cnpj = '%s'", editora_antes.getCnpj()));
        ArrayList<Publisher> editoras = crud.read(db, Optional.of(conditions), Optional.of(" AND "));
        Publisher editora_update = editoras.get(0); 
        
        editora_update.setNome(nome);
        editora_update.setCnpj(cnpj);
        editora_update.setTelefone(telefone);

        conditions.clear();
        conditions.add(String.format("cnpj = '%s'", editora_antes.getCnpj()));

        crud.update(db, editora_update, Optional.of(conditions));
        editorasObs.set(i, editora_update);
            
        
    }

    

    @FXML 
    public void adicionarEditora(MouseEvent event){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

        String nome, cnpj, telefone;
        Publishers crud  = new Publishers();

        try {

            nome = nomeTextField.getText();
            cnpj = cnpjTextField.getText();
            telefone = telefoneTextField.getText();
            Publisher editora = new Publisher(nome, cnpj, telefone);

            editoras.add(editora);
            editorasObs.add(editora);
            crud.create(editora, db);


            resetTextFields();

        } catch (Exception e) {

            alert.setContentText("Ocorreu um erro");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    public void removerEditora(MouseEvent event){
        ArrayList<String> conditions = new ArrayList<>();
        Publishers crud = new Publishers();
        String cnpj;

        int i = tableEditora.getSelectionModel().getSelectedIndex();
        
        if(i<0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.setContentText("Selecione uma linha para remover");
            alert.showAndWait();
            return;
        }

        editorasObs.remove(i);

        cnpj = "\'" + editoras.get(i).getCnpj() + "\'";
        conditions.add(String.format("cnpj = %s", cnpj));
        crud.delete(db, conditions); 
        
        resetTextFields();
    }

    @FXML
    public void pesquisarEditora(MouseEvent event){
        Publishers crud = new Publishers();
        ObservableList<Publisher> filter = FXCollections.observableArrayList();

        String filtro = filtroTextField.getText();
        ArrayList<String> like = new ArrayList<>();

        
        if(filtroTextField.getText().equals("")){
            tableEditora.setItems(editorasObs);
        }
        else{
            like.add("UPPER(nome) LIKE "+"UPPER('%"+filtro+ "%')");
            like.add("cnpj LIKE "+"'%"+filtro+ "%'");
            like.add("telefone LIKE "+"'%"+filtro+ "%'");
    
            editoras = crud.read(db, Optional.of(like), Optional.of(" OR "));

            for (Publisher editora : editoras) {
                    filter.add(editora);
            }
            tableEditora.setItems(filter);
        }

        resetTextFields();
    
    }
    @FXML
    void pesquisarEditora2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            
            if(filtroTextField.getText().equals("")){
                tableEditora.setItems(editorasObs);
            }
            else{
                Publishers crud = new Publishers();
                ObservableList<Publisher> filter = FXCollections.observableArrayList();
        
                String filtro = filtroTextField.getText();
                ArrayList<String> like = new ArrayList<>();
    
                like.add("UPPER(nome) LIKE "+"UPPER('%"+filtro+ "%')");
                like.add("cnpj LIKE "+"'%"+filtro+ "%'");
                like.add("telefone LIKE "+"'%"+filtro+ "%'");
    
                editoras = crud.read(db, Optional.of(like), Optional.of(" OR "));

                for (Publisher editora : editoras) {
                        filter.add(editora);
                }
                tableEditora.setItems(filter);
            }

            resetTextFields();

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
    public void changePageUsers(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AdminUsers.fxml"));
        root = loader.load();

        AdminUsersController adminUsersController = loader.getController();
        adminUsersController.initializeDB(db);
        adminUsersController.init();

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
        int i = tableEditora.getSelectionModel().getSelectedIndex();
        if(i>-1){

            
            Publisher editora = (Publisher) tableEditora.getItems().get(i);
            
            nomeTextField.setText(editora.getNome());
            cnpjTextField.setText(editora.getCnpj());
            telefoneTextField.setText(editora.getTelefone());
        }
    }

    public void resetTextFields(){
        nomeTextField.setText("");
        cnpjTextField.setText("");
        telefoneTextField.setText("");
        filtroTextField.setText("");
    }    


}
