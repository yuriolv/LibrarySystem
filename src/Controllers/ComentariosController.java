package Controllers;

import Classes.RentBook;
import Models.Comments;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ComentariosController {

    private RentBook rent;
    @FXML
    private TextArea comentarioTextArea;


    public void enviarComentario(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Comments crud = new Comments();

        if(comentarioTextArea.getText().equals("")) {
            stage.close();
        } 
            String comentario = comentarioTextArea.getText();
            crud.createComment(comentario, rent.getTitulo());
            stage.close();
        
    }

    public void setData(RentBook rent){
        this.rent=rent;
    }
}
 