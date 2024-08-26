package Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Classes.*;
import DB.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RentReportController {

    @FXML
    private Label dataAluguelLabel;

    @FXML
    private Label dataDevoluçãoLabel;

    @FXML
    private Label matriculaLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label tituloLabel;
    private Book selected_livro;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    public void setData(Book book){
        selected_livro = book;
    }


    
    public void setLabels(User user, RentBook rent){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dateForDevolution = LocalDate.parse(rent.getDateRent(), dateTimeFormatter);

        String data;

        dateForDevolution = dateForDevolution.plusDays(7);

        data = dateForDevolution.format(dateTimeFormatter);

        nomeLabel.setText(user.getNome());
        matriculaLabel.setText(user.getMatricula());
        tituloLabel.setText(selected_livro.getTitulo());
        dataAluguelLabel.setText(rent.getDateRent());
        dataDevoluçãoLabel.setText(data);
       

    }
}
