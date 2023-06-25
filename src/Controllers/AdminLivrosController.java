package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

public class AdminLivrosController {

    @FXML
    private TableColumn<?, ?> assuntoColumn;

    @FXML
    private TableColumn<?, ?> autorColumn;

    @FXML
    private TableColumn<?, ?> editarColumn;

    @FXML
    private TableColumn<?, ?> estoqueColumn;

    @FXML
    private TableColumn<?, ?> removerColumn;

    @FXML
    private TableColumn<?, ?> tituloColumn;

    @FXML
    void changePageAdmin(ActionEvent event) {
        App.changeScene("pageAdmin");

    }

    @FXML
    void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
    }

}
