package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TelaInicialController implements Initializable {

    @FXML
    private AnchorPane acervoPane;

    @FXML
    private AnchorPane bibliotecarioPane;

    @FXML
    private AnchorPane usuariosPane;

    

    @FXML
    void redirecionarTelaAcervo(MouseEvent event) {
    }

    @FXML
    void redirecionarTelaBibliotecario(MouseEvent event) {

    }

    @FXML
    void redirecionarTelaUsers(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        acervoPane.setCursor(Cursor.HAND);
        bibliotecarioPane.setCursor(Cursor.CLOSED_HAND);
        usuariosPane.setCursor(Cursor.CLOSED_HAND);
    }

}
