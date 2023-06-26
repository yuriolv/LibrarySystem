package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Classes.Livro;
import Models.Livros;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminLivrosController  implements Initializable{

    @FXML
    private TableView<Livro> tableLivros;

    @FXML
    private TableColumn<Livro, String> assuntoColumn;

    @FXML
    private TableColumn<Livro, String> autorColumn;

    @FXML
    private TableColumn<Livro, String> tituloColumn;
    
    @FXML
    private TableColumn<Livro, Integer> estoqueColumn;
    
    @FXML
    private TableColumn<Livro, Button> removerColumn;

    @FXML
    private TableColumn<Livro, Button> editarColumn;

    private Button but;

    ObservableList<Livro> livros = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Livros crud = new Livros();
        
        crud.read(livros);

        tituloColumn.setCellValueFactory(new PropertyValueFactory<Livro, String>("Titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<Livro, String>("Autor"));
        assuntoColumn.setCellValueFactory(new PropertyValueFactory<>("Assunto"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("QtdEstoque"));

        tableLivros.setItems(livros);
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
    void editarLivro(ActionEvent event) {
        



    }

    @FXML
    void removerLivro(ActionEvent event) {
        Livros crud = new Livros();
        
        int indice = tableLivros.getSelectionModel().getSelectedIndex();

        crud.delete(indice, livros);
    }

    


}
