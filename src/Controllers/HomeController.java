package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomeController{

    @FXML
    public void changePageAcervo(MouseEvent event) {
        App.changeScene("pageAcervo");
    }

    @FXML
    public void changeLoginAdmin(MouseEvent event) {
        App.changeScene("loginAdmin");
    }

    @FXML
    public void changeLoginUser(MouseEvent event) {
        App.changeScene("loginUser");
    }


}
