package Controllers;

import Classes.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InvoiceUserController {
    
    @FXML
    private Label Livro1Label;
    
    @FXML
    private Label livro2Label;
    
    @FXML
    private Label livro3Label;
    
    @FXML
    private Label devolução1Label;

    @FXML
    private Label devolução2Label;

    @FXML
    private Label devolução3Label;

    @FXML
    private Label emprestimo1Label;

    @FXML
    private Label emprestimo2Label;

    @FXML
    private Label emprestimo3Label;

    @FXML
    private Label nomeLabel;
    
    @FXML
    private Label matriculaLabel;

    @FXML
    private Label multaTotalLabel;
    @FXML
    private Label naoUteisLabel;
    @FXML
    private Label uteisLabel;



    public void setLabels(User user){
        nomeLabel.setText(user.getNome());
        matriculaLabel.setText(user.getMatricula());

    }

}
