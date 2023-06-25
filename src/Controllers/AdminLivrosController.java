package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Classes.Livro;
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
    private TableColumn<Livro, String> autorColumn;

    @FXML
    private TableColumn<Livro, String> tituloColumn;
    
    @FXML
    private TableColumn<Livro, Integer> estoqueColumn;
    
    @FXML
    private TableColumn<Livro, Button> removerColumn;

    @FXML
    private TableColumn<Livro, Button> editarColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("Estoque"));
        tableLivros.setItems(booksList);
    }


    ObservableList<Livro> booksList = FXCollections.observableArrayList(
        new Livro("Banco de dados para web", "Ana Luiza", "Banco de dados", 4)
    );


    @FXML
    void changePageAdmin(ActionEvent event) {
        App.changeScene("pageAdmin");

    }

    @FXML
    void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
        
    }


}
