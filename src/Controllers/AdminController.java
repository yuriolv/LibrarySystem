package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AdminController {


    @FXML
    void ChangePaneUsersTable(MouseEvent event) {

    }

    @FXML
    void changePageAcervoTable(MouseEvent event) {
        App.changeScene("pageAdminLivros");

    }

    @FXML
    void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
    }

    @FXML
    void changePageUserTable(MouseEvent event) {

    }

}

