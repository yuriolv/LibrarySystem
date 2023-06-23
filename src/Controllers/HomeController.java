package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomeController{

    @FXML
    void changePageAcervo(MouseEvent event) {
        App.changeScene("pageAcervo");
    }

    @FXML
    void changeLoginAdmin(MouseEvent event) {
        App.changeScene("loginAdmin");
    }

    @FXML
    void changeLoginUser(MouseEvent event) {
        App.changeScene("loginUser");
    }


}
