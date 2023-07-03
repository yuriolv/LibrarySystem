package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Classes.Livro;
import Classes.RentBook;
import Classes.User;
import Models.Rents;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class DevolucaoController implements Initializable{

    @FXML
    private TableColumn<RentBook, String> dataEmprestimo;

    @FXML
    private TableColumn<RentBook, String> nomeLivro;
    
    @FXML
    private Button botao;
    @FXML
    private TableView<Livro> tabela;

    private User user;


    public void metodo(MouseEvent event) {
        int i = tabela.getSelectionModel().getSelectedIndex();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rents crud = new Rents();

        ArrayList<RentBook> rents = new ArrayList<RentBook>();
        ArrayList<RentBook> rentsUser = new ArrayList<RentBook>(); 

        crud.read(rents);

        for(RentBook rent: rents) {
            if()
        }
    }

    public void setData(User user) {
        this.user = user;
    }
}
