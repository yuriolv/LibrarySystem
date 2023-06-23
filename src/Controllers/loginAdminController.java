package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class loginAdminController {
    @FXML
    public void changePageHome(MouseEvent e){
        App.changeScene("pageHome");
    }
}
