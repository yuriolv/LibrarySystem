package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AdminController {

    
    @FXML
    public void changePageHome(MouseEvent event) {
        App.changeScene("pageHome");
    }

    @FXML
    public void changePageBooks(MouseEvent event) {
        App.changeScene("pageAdminBooks");
    }

    @FXML
    public void changePageUsers(MouseEvent event) {
        App.changeScene("pageAdminUsers");
    }

}

