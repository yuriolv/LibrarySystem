package Controllers;

import Classes.*;
import DB.DataBase;
import Models.Comments;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ComentariosController {

    private RentBook rent;
    @FXML
    private TextArea comentarioTextArea;
    private DataBase db;
    private Book selected_livro;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }


    public void enviarComentario(MouseEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Comments crud = new Comments();

        if(comentarioTextArea.getText().equals("")) {
            stage.close();
        } 

        Comment comentario = new Comment(rent.getId_livro(),rent.getMatricula(), comentarioTextArea.getText());
        crud.create(db, comentario);
        stage.close();
        
    }

    public void setData(RentBook rent){
        this.rent=rent;
    }
}
 