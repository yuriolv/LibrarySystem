package Controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Classes.Livro;
import Models.Livros;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;


public class AdminLivrosController  implements Initializable{

    @FXML
    private TableView<Livro> tableLivros;

    @FXML
    private TableColumn<Livro, String> tituloColumn;
    
    @FXML
    private TableColumn<Livro, String> autorColumn;
    
    @FXML
    private TableColumn<Livro, String> assuntoColumn;
    
    @FXML
    private TableColumn<Livro, Integer> estoqueColumn;

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
    private Label responseLabel;

    @FXML
    private Label responseLabel2;



    private ArrayList<Livro> livros;

    private ObservableList<Livro> livrosObs; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Livros crud = new Livros();
    
        livros = new ArrayList<Livro>();
        crud.read(livros);

        livrosObs = FXCollections.observableArrayList(livros);

        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        assuntoColumn.setCellValueFactory(new PropertyValueFactory<>("Assunto"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("QtdEstoque"));

        tableLivros.setItems(livrosObs);
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
    void adicionarLivro(MouseEvent event) {

        Livros crud = new Livros();

        crud.read(livros);

        String titulo, autor, assunto;
        int estoque;
        try{

            /* String imagePath = chooseImage();

            System.out.println(imagePath); */

            titulo=  tituloTextField.getText();
            autor = autorTextField.getText();
            assunto = assuntoTextField.getText();
            
            if(titulo.equals("") || autor.equals("") || assunto.equals("") || estoqueTextField.getText().equals("") ) {
                responseLabel.setText("Preencha todos os campos!");
                return; 
            }

            estoque = Integer.parseInt(estoqueTextField.getText());
            
            resetTextFields();

            //adicionar imagem ao construtor
            Livro livro = new Livro(titulo, autor, assunto, estoque);
            
            livros.add(livro);
            livrosObs.add(livro);
            crud.create(livro);
        }catch(NumberFormatException e){
            responseLabel2.setText("O campo 'estoque' deve conter apenas números!");
        }
        

    }

    @FXML
    public void editarLivro(MouseEvent event) {
        int i = tableLivros.getSelectionModel().getSelectedIndex();
        Livros crud = new Livros();
        String titulo, autor, assunto;
        int estoque;
        
        tableLivros.setItems(livrosObs);


        try{ 
            titulo=  tituloTextField.getText();
            autor = autorTextField.getText();
            estoque = Integer.parseInt(estoqueTextField.getText());
            assunto = assuntoTextField.getText();
            
            Livro livro = new Livro(titulo, autor, assunto, estoque);

            livros.set(i, livro);
            livrosObs.set(i, livro);
            
            crud.update(livros);
        
            resetTextFields();
        }catch(NumberFormatException e){
            System.out.println("Lembrete: digite apenas numeros");
        }

    }
    @FXML
    public void pesquisarLivro(MouseEvent event) {
        Livros crud = new Livros();
        ObservableList<Livro> filter = FXCollections.observableArrayList();

        crud.read(livros);
        
        if(filtroTextField.getText().equals("")){
            
            tableLivros.setItems(livrosObs);
        }
        else{
            String filtro = filtroTextField.getText();

            for (Livro livro : livros) {
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
        if(event.getCode() == KeyCode.ENTER){
            Livros crud = new Livros();
            ObservableList<Livro> filter = FXCollections.observableArrayList();
    
            crud.read(livros);
            
            if(filtroTextField.getText().equals("")){
                
                tableLivros.setItems(livrosObs);
            }
            else{
                String filtro = filtroTextField.getText();
    
                for (Livro livro : livros) {
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
        Livros crud = new Livros();
        
        int i = tableLivros.getSelectionModel().getSelectedIndex();

        
        livrosObs.remove(i);
        crud.delete(i, livros);

    }


    @FXML
    void getRowData(MouseEvent event) {

        int i = tableLivros.getSelectionModel().getSelectedIndex();
    
        Livro livro = (Livro) tableLivros.getItems().get(i);
    
            tituloTextField.setText(livro.getTitulo());
            autorTextField.setText(livro.getAutor());
            assuntoTextField.setText(livro.getAssunto());
            String estoque = String.valueOf(livro.getQtdEstoque());
            
            estoqueTextField.setText(estoque);
    

    }

    
    public String chooseImage(){
        try {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);
    
            if(file == null ){
                return "arquivo corrompido";
            } 

            String typeFile = Files.probeContentType(file.toPath());
            
            if(typeFile.equals("image/png") || typeFile.equals("image/jpeg" )) {
                return file.getPath(); //retornar o caminho relativo
            } else {
                return "arquivo invalido";
            }

        } catch (Exception e) {
            System.out.println(e);
            return "arquivo corrompido";
        }
    }

    public void resetTextFields(){
        tituloTextField.setText("");
        autorTextField.setText("");
        assuntoTextField.setText("");
        estoqueTextField.setText("");
        responseLabel.setText("");
        filtroTextField.setText("");
    }    


}
