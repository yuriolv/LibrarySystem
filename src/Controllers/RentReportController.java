package Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Classes.RentBook;
import Classes.User;
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


    
    public void setLabels(User user, RentBook rent){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dateForDevolution = LocalDate.parse(rent.getDateRent(), dateTimeFormatter);

        String data;

        dateForDevolution = dateForDevolution.plusDays(7);

        data = dateForDevolution.format(dateTimeFormatter);

        nomeLabel.setText(user.getNome());
        matriculaLabel.setText(user.getMatricula());
        tituloLabel.setText(rent.getTitulo());
        dataAluguelLabel.setText(rent.getDateRent());
        dataDevoluçãoLabel.setText(data);
       

    }
}
