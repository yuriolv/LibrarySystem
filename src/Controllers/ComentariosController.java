package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ComentariosController {
    @FXML
    private TextArea textArea;
    @FXML
    private Button button;

    public void enviarComentario() {
        if(textArea.getText().equals("")) {
            
        }
    }
}
 