package Controllers;

import Models.Comments;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ComentariosController {
    @FXML
    private TextArea textArea;
    @FXML
    private Button button;

    public void enviarComentario(MouseEvent event) {
        Comments crud = new Comments();

        if(textArea.getText().equals("")) {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }

        else {
            String comentario = textArea.getText();
            crud.createComment(comentario);
            
        }
    }
}
 