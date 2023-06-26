package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class UserController {
     @FXML
    public void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
    }

    @FXML
    public void changeRentBook(MouseEvent event) {
        App.changeScene("pageAdminLivros");
    }

    @FXML
    public void changePageDevolution(MouseEvent event) {
        App.changeScene("pageAdminUsers");
    }

    @FXML
    public void changeGenerateReport(MouseEvent event) {
        App.changeScene("pageAdminUsers");
    }
}
