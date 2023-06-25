package Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuUserController {



    @FXML
    void returnPageHome(MouseEvent event) {
        App.changeScene("pageHome");
    }

}

