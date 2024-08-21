package Controllers;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Classes.Book;
import DB.DataBase;
import Models.Books;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Screen;


public class AdminLivrosController  implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Book> tableLivros;
    @FXML
    private TableColumn<Book, String> tituloColumn;
    @FXML
    private TableColumn<Book, String> autorColumn;
    @FXML
    private TableColumn<Book, String> assuntoColumn;
    @FXML
    private TableColumn<Book, String> coleçãoColumn;
    @FXML
    private TableColumn<Book, Integer> estoqueColumn;
    @FXML
    private TextField tituloTextField;
    @FXML
    private TextField autorTextField;
    @FXML
    private TextField assuntoTextField;
    @FXML
    private TextField estoqueTextField;
    @FXML
    private TextField filtroTextField;
    @FXML
    private ToggleGroup coleção;
    @FXML
    private RadioButton radioComum;
    @FXML
    private RadioButton radioEspecial;


    private ArrayList<Book> livros;
    private ObservableList<Book> livrosObs; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataBase db = new DataBase();
        db.initialize();
        Books crud_Books = new Books();
        livros = crud_Books.read(db, Optional.empty());

        livrosObs = FXCollections.observableArrayList(livros);

        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        assuntoColumn.setCellValueFactory(new PropertyValueFactory<>("Assunto"));
        coleçãoColumn.setCellValueFactory(new PropertyValueFactory<>("Coleção"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("QtdEstoque"));

        tableLivros.setItems(livrosObs);
    }

    @FXML
    public void changePageAdmin(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../Views/Admin.fxml"));
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
    public void changePageHome(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
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
    void adicionarLivro(MouseEvent event, DataBase db) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");
        Books crud = new Books();

        livros = crud.read(db, Optional.empty());

        String titulo, autor, assunto;
        int estoque;
        RadioButton radio = (RadioButton) coleção.getSelectedToggle();
        if(radio==null){
            alert.setContentText("Selecione um radio Button");
            alert.showAndWait();
            return;

        }
            String selected = radio.getText();
        try{


            titulo=  tituloTextField.getText();
            autor = autorTextField.getText();
            assunto = assuntoTextField.getText();
            
            if(titulo.equals("") || autor.equals("") || assunto.equals("") || estoqueTextField.getText().equals("") ) {
                alert.setContentText("Preencha todos os campos");
                alert.showAndWait();
                return;
            }

            byte[] image = chooseImage();

            if(image == null){
                alert.setContentText("Selecione uma imagem valida!");
                alert.showAndWait();
                return;
            }

            estoque = Integer.parseInt(estoqueTextField.getText());
            
            resetTextFields();


            Book livro = new Book(titulo, autor, assunto, estoque, selected,image);
            
            livros.add(livro);
            livrosObs.add(livro);
            crud.create(livro, db);

        }catch(NumberFormatException e){
            alert.setContentText("O campo estoque deve conter somente numeros");
            alert.showAndWait();
            return;
        }

    }

    @FXML
    public void editarLivro(MouseEvent event, DataBase db) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

        int i = tableLivros.getSelectionModel().getSelectedIndex();
        Books crud = new Books();
        String titulo, autor, assunto;
        int estoque;
        RadioButton radio = (RadioButton) coleção.getSelectedToggle();
        
        tableLivros.setItems(livrosObs);


        if(radio!=null){
            String selected = radio.getText();
            try{ 
            
                titulo=  tituloTextField.getText();
                autor = autorTextField.getText();
                assunto = assuntoTextField.getText();

            
                if(titulo.equals("") || autor.equals("") || assunto.equals("") || estoqueTextField.getText().equals("") ) {
                    alert.setContentText("Preencha todos os campos!");
                    alert.showAndWait();
                    return; 
                }

                byte[] imagePath = chooseImage();

                if(imagePath.equals("arquivo corrompido") || imagePath.equals("arquivo invalido")){
                    alert.setContentText("Escolha uma imagem válida");
                    alert.showAndWait();
                    return;
                }

                estoque = Integer.parseInt(estoqueTextField.getText());

                resetTextFields();

                Book livro = new Book(titulo, autor, assunto, estoque, selected, imagePath);

                livros.set(i, livro);
                crud.update(db,);
                livrosObs.set(i, livro);
            
        
            }catch(NumberFormatException e){
                alert.setContentText("O campo estoque deve conter somente numeros");
                alert.showAndWait();
            }
        }

    }
    @FXML
    public void pesquisarLivro(MouseEvent event, DataBase db) {
        Books crud = new Books();
        ObservableList<Book> filter = FXCollections.observableArrayList();

        livros = crud.read(db, Optional.empty());
        
        if(filtroTextField.getText().equals("")){
            
            tableLivros.setItems(livrosObs);
        }
        else{
            String filtro = filtroTextField.getText();

            for (Book livro : livros) {
                if(livro.getTitulo().equals(filtro)
                 ||livro.getAutor().equals(filtro)
                 ||livro.getAssunto().equals(filtro)
                 ||String.valueOf(livro.getQtdEstoque()).equals(filtro)){

                    filter.add(livro);
                 }
            }
            tableLivros.setItems(filter);
        }
        resetTextFields();

            
    }
    @FXML
    void pesquisarLivro2(KeyEvent event) {
        DataBase db = new DataBase();
        db.initialize();
        if(event.getCode() == KeyCode.ENTER){
            Books crud = new Books();
            ObservableList<Book> filter = FXCollections.observableArrayList();
    
            livros = crud.read(db, Optional.empty());
            
            if(filtroTextField.getText().equals("")){
                
                tableLivros.setItems(livrosObs);
            }
            else{
                String filtro = filtroTextField.getText();
    
                for (Book livro : livros) {
                    if(livro.getTitulo().equals(filtro)
                     ||livro.getAutor().equals(filtro)
                     ||livro.getAssunto().equals(filtro)
                     ||String.valueOf(livro.getQtdEstoque()).equals(filtro)){
    
                        filter.add(livro);
                     }
                }
                tableLivros.setItems(filter);
            }
            resetTextFields();



            
        }
    }


    @FXML
    void removerLivro(MouseEvent event) {
        Books crud = new Books();
        
        int i = tableLivros.getSelectionModel().getSelectedIndex();
        if(i<0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.setContentText("Selecionea linha que deseja remover");
            alert.showAndWait();
            return;
        }
        livrosObs.remove(i);
        crud.delete(i, livros);

    }


    @FXML
    void getRowData(MouseEvent event) {

        int i = tableLivros.getSelectionModel().getSelectedIndex();
        if(i>-1){

            Book livro = (Book) tableLivros.getItems().get(i);
            String estoque = String.valueOf(livro.getQtdEstoque());
        
            tituloTextField.setText(livro.getTitulo());
            autorTextField.setText(livro.getAutor());
            assuntoTextField.setText(livro.getAssunto());

           if(livro.getColeção().equals("Coleção Comum"))
                radioComum.setSelected(true);
            else
                radioEspecial.setSelected(true);
            
            
            estoqueTextField.setText(estoque);
    
        }

    }

    
    public byte[] chooseImage(){
        try {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);
            if(file == null ){
                return null;
            } 

            byte [] image = Files.readAllBytes(file.toPath());
            String fileName = file.getName();

            int index = fileName.lastIndexOf(".");
            String extension = fileName.substring(index + 1);

            if(extension.equals("jpg") || extension.equals("png") ||  extension.equals("jpeg")) {
                return image;
            } else {
                return null;
            }

            

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void resetTextFields(){
        tituloTextField.setText("");
        autorTextField.setText("");
        assuntoTextField.setText("");
        estoqueTextField.setText("");
        filtroTextField.setText("");

        if(coleção.getSelectedToggle() == null)
            return;
        coleção.getSelectedToggle().setSelected(false);
    }    


}
