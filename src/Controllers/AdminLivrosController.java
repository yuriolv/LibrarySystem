package Controllers;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Optional;


import Classes.Book;
import Classes.Publisher;
import DB.DataBase;
import Models.Books;
import Models.Publishers;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javax.swing.ComboBoxEditor;


public class AdminLivrosController  {
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
    private TableColumn<Book, String> editoraColumn;
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
    private ComboBox<Publisher> editoraComboBox;
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

    public DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    
    public void init() {
        Books crud_Books = new Books();
        
        livros = crud_Books.read(db, Optional.empty(), Optional.empty());

        livrosObs = FXCollections.observableArrayList(livros);

        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        assuntoColumn.setCellValueFactory(new PropertyValueFactory<>("Assunto"));
        coleçãoColumn.setCellValueFactory(new PropertyValueFactory<>("Coleção"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("QtdEstoque"));
        editoraColumn.setCellValueFactory(cellData ->{
            Book livro = cellData.getValue();  
            String editoraNome = livro.getEditoraNome(db);

            return new SimpleStringProperty(editoraNome);
        });

        tableLivros.setItems(livrosObs);
        setComboData();
    }

    @FXML
    public void changePageAdmin(MouseEvent event) throws IOException{
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
    public void changePageHome(MouseEvent event) throws IOException{
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
    public void adicionarLivro(MouseEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

        Books crud_Books = new Books();
        Book livro;
        String titulo, autor, assunto, colecao_selecionada;
        int qtd_estoque;

        RadioButton radio = (RadioButton) coleção.getSelectedToggle();
        Publisher editora = editoraComboBox.getSelectionModel().getSelectedItem();
        if(radio==null){
            alert.setContentText("Selecione um radio Button");
            alert.showAndWait();
            return;
        }
        if(editora==null){
            alert.setContentText("Selecione uma editora");
            alert.showAndWait();
            return;
        }
        colecao_selecionada = radio.getText();
        

        try{


            titulo =  tituloTextField.getText();
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

            qtd_estoque = Integer.parseInt(estoqueTextField.getText());
            
            resetTextFields();


            
            livro = new Book(autor, titulo, assunto, qtd_estoque, colecao_selecionada, image, editora.getId());
            livrosObs.add(livro);
            crud_Books.create(livro, db);

        }catch(NumberFormatException e){
            alert.setContentText("O campo estoque deve conter somente numeros");
            alert.showAndWait();
            return;
        }

    }

    @FXML
    public void editarLivro(MouseEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro");

        int i = tableLivros.getSelectionModel().getSelectedIndex();
        Book livro_antes = (Book) tableLivros.getItems().get(i);

        Books crud = new Books();
        String titulo, autor, assunto;
        int estoque;
        RadioButton radio = (RadioButton) coleção.getSelectedToggle();
        Publisher editora = editoraComboBox.getSelectionModel().getSelectedItem();
        tableLivros.setItems(livrosObs);


        if(radio!=null && editora!=null){
            try{ 
                
                String selected = radio.getText();
                autor = autorTextField.getText();
                titulo =  tituloTextField.getText();
                assunto = assuntoTextField.getText();

            
                if(titulo.equals("") || autor.equals("") || assunto.equals("") || estoqueTextField.getText().equals("") ) {
                    alert.setContentText("Preencha todos os campos!");
                    alert.showAndWait();
                    return; 
                }

                byte[] image = chooseImage();

                if((image == null)){
                    alert.setContentText("Escolha uma imagem válida");
                    alert.showAndWait();
                    return;
                }

                estoque = Integer.parseInt(estoqueTextField.getText());

                resetTextFields();

                ArrayList<String> conditions = new ArrayList<>();
                conditions.add("autor = "+ "'"+livro_antes.getAutor()+"'");
                conditions.add("titulo = "+ "'"+livro_antes.getTitulo()+"'");

                ArrayList<Book> books = crud.read(db, Optional.of(conditions), Optional.of(" AND "));
                Book livro_update = books.get(0); 

                livro_update.setAutor(autor);
                livro_update.setTitulo(titulo);
                livro_update.setAssunto(assunto);
                livro_update.setQtdEstoque(estoque);
                livro_update.setImage(image);
                livro_update.setColeção(selected);
                livro_update.setEditora(editora.getId());

                conditions.clear();
                conditions.add("\"ID\" = " +livro_update.getID());

                crud.update(db, livro_update, Optional.of(conditions));
                livrosObs.set(i, livro_update);
            
        
            }catch(NumberFormatException e){
                alert.setContentText("O campo estoque deve conter somente numeros");
                alert.showAndWait();
            }
        }

    }
    @FXML
    public void pesquisarLivro(MouseEvent event) {
        Books crud = new Books();
        ObservableList<Book> filter = FXCollections.observableArrayList();

        String filtro = filtroTextField.getText();
        ArrayList<String> like = new ArrayList<>();

        
        if(filtroTextField.getText().equals("")){
            tableLivros.setItems(livrosObs);
        }
        else{
            like.add("UPPER(autor) LIKE "+"UPPER('%"+filtro+ "%')");
            like.add("UPPER(titulo) LIKE "+"UPPER('%"+filtro+ "%')");
            like.add("UPPER(assunto) LIKE "+"UPPER('%"+filtro+ "%')");
            like.add("UPPER(colecao) LIKE "+"UPPER('%"+filtro+ "%')");
    
            livros = crud.read(db, Optional.of(like), Optional.of(" OR "));

            for (Book livro : livros) {
                    filter.add(livro);
            }
            tableLivros.setItems(filter);
        }

        resetTextFields();
    
    }
    @FXML
    public void pesquisarLivro2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            
            if(filtroTextField.getText().equals("")){
                tableLivros.setItems(livrosObs);
            }
            else{
                Books crud = new Books();
                ObservableList<Book> filter = FXCollections.observableArrayList();
        
                String filtro = filtroTextField.getText();
                ArrayList<String> like = new ArrayList<>();
    
                like.add("UPPER(autor) LIKE "+"UPPER('%"+filtro+ "%')");
                like.add("UPPER(titulo) LIKE "+"UPPER('%"+filtro+ "%')");
                like.add("UPPER(assunto) LIKE "+"UPPER('%"+filtro+ "%')");
                like.add("UPPER(colecao) LIKE "+"UPPER('%"+filtro+ "%')");
    
                livros = crud.read(db, Optional.of(like), Optional.of(" OR "));

                for (Book livro : livros) {
                        filter.add(livro);
                }
                tableLivros.setItems(filter);
            }

            resetTextFields();



            
        }
    }


    @FXML
    public void removerLivro(MouseEvent event) {
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
        String titulo =  tituloTextField.getText();
        livrosObs.remove(i);
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("titulo = " + "'"+titulo+"'");

        crud.delete(db, conditions);

    }


    @FXML
    public void getRowData(MouseEvent event) {

        int i = tableLivros.getSelectionModel().getSelectedIndex();
        if(i>-1){

            Book livro = (Book) tableLivros.getItems().get(i);
            String estoque = String.valueOf(livro.getQtdEstoque());
        
            autorTextField.setText(livro.getAutor());
            tituloTextField.setText(livro.getTitulo());
            assuntoTextField.setText(livro.getAssunto());
            for (Publisher editora: editoraComboBox.getItems()){
                if(livro.getEditora()==editora.getId()){
                    editoraComboBox.setValue(editora);
                    break;
                }
            }


           if(livro.getColeção().equals("Coleção Comum"))
                radioComum.setSelected(true);
            else
                radioEspecial.setSelected(true);
            
            
            estoqueTextField.setText(estoque);
    
        }

    }
    public void setComboData(){
        Publishers pcrud = new Publishers();
        ArrayList<Publisher> editoras = pcrud.read(db, Optional.empty(), Optional.empty());
        ObservableList<Publisher> editorasObs = FXCollections.observableArrayList(editoras);
        editoraComboBox.setItems(editorasObs);
    }
    
    public byte[] chooseImage(){
        try {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);
            if(file == null ){
                return null;
            } 
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            String fileName = file.getName();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            
            int index = fileName.lastIndexOf(".");
            String extension = fileName.substring(index + 1);
            
            if(extension.equals("jpg") || extension.equals("png") ||  extension.equals("jpeg")) {
                fis.close();
                return bos.toByteArray();
            } else {
                fis.close();
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
        editoraComboBox.valueProperty().set(null);

        if(coleção.getSelectedToggle() == null)
            return;
        coleção.getSelectedToggle().setSelected(false);
    }    


}
