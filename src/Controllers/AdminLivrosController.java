package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Classes.Livro;
import Models.Livros;

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
    void changePageAdmin(MouseEvent event) {
        App.changeScene("pageAdmin");

    }

    @FXML
    void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
        
    }


    @FXML
    void adicionarLivro(ActionEvent event) {
        ArrayList<Livro> livros = new ArrayList<Livro>();

        Livros crud = new Livros();
        String titulo, autor, assunto;
        int estoque;
        try{

            titulo=  tituloTextField.getText();
            autor = autorTextField.getText();
            assunto = assuntoTextField.getText();
            estoque = Integer.parseInt(estoqueTextField.getText());
            
            Livro livro = new Livro(titulo, autor, assunto, estoque);
            
            livros.add(livro);
            livrosObs.add(livro);
            crud.create(livro);
        }catch(NumberFormatException e){
            System.out.println("Lembrete: digite apenas numeros");
        }
        

    }

    @FXML
    void editarLivro(ActionEvent event) {
        int i = tableLivros.getSelectionModel().getSelectedIndex();
        Livros crud = new Livros();
        String titulo, autor, assunto;
        int estoque;

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
    void pesquisarLivro(ActionEvent event) {
      
            
    }

    @FXML
    void removerLivro(ActionEvent event) {
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

    public void resetTextFields(){
        tituloTextField.setText("");
        autorTextField.setText("");
        assuntoTextField.setText("");
        estoqueTextField.setText("");
    }    


}
