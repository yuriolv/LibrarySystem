package Controllers;

import java.util.ArrayList;

import Classes.RentBook;
import Classes.User;
import DB.DataBase;
import Models.Rents;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InvoiceUserController {

    @FXML
    private Label nomeLabel;
    
    @FXML
    private Label matriculaLabel;

    @FXML
    private Label multaLabel;
   
    @FXML
    private Label atrasoLabel;

    @FXML
    private HBox livro1;
    @FXML
    private HBox livro2;
    @FXML
    private HBox livro3;

    private ArrayList<RentBook> rents;
    private User user;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }


    public void getRents(){
        int i = 0;
        double multa = 0;
        double dias = 0;
        rents = new ArrayList<>();
        
        Rents crud = new Rents();
        crud.read(rents);
        
        for (RentBook rentBook : rents) {
            if(rentBook.getMatricula().equals(user.getMatricula())){
                i++;
                Label titulo = new Label(rentBook.getTitulo());
                Label dateRent = new Label(rentBook.getDateRent());
                Label dateDevolution = new Label(rentBook.setDateForDevolution());

                titulo.setPrefSize(170, 20);
                titulo.setStyle("-fx-font-size: 14;");

                dateRent.setPrefSize(97, 20);
                dateRent.setStyle("-fx-font-size: 14;");

                dateDevolution.setPrefSize(80, 20);
                dateDevolution.setStyle("-fx-font-size: 14;");
                

                if(i == 1){
                    livro1.getChildren().addAll(titulo, dateRent, dateDevolution);
                } else if(i == 2){
                    livro2.getChildren().addAll(titulo, dateRent, dateDevolution);
                }else {
                    livro3.getChildren().addAll(titulo, dateRent, dateDevolution);
                }

                multa += rentBook.getMulta();


                if(rentBook.getTipo().equals("Discente")){
                    dias = multa/0.5;
                }else{
                    dias = multa/0.8;
                }
            }

        }
        multaLabel.setText(multa+"");
        atrasoLabel.setText(dias+"");
    }



    public void setLabels(){
        getRents();
        nomeLabel.setText(user.getNome());
        matriculaLabel.setText(user.getMatricula());

        

    }

    public void setData(User user){
        this.user = user;
    }

}
