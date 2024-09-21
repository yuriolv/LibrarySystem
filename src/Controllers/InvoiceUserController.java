package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Classes.RentBook;
import Classes.User;
import DB.DataBase;
import Models.Rents;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InvoiceUserController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label nomeLabel;
    
    @FXML
    private Label tipoLabel;
    

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
        Rents crud = new Rents();

        rents = crud.read(db, Optional.empty());
        
        for (RentBook rentBook : rents) {

            if(rentBook.getMatricula().equals(user.getMatricula())){
                i++;
                Label titulo = new Label(rentBook.getTitulo(db)); //pegar titulo a partir do id
                Label dateRent = new Label(rentBook.getDateRent());
                Label dateDevolution = new Label(rentBook.getDateDevolution());

                titulo.setPrefSize(170, 20);
                HBox.setMargin(titulo, new Insets(0, 0, 0, 41));
                titulo.setStyle("-fx-font-size: 16;");

                dateRent.setPrefSize(97, 20);
                HBox.setMargin(dateRent, new Insets(0, 0, 0, 160));
                dateRent.setStyle("-fx-font-size: 16;");

                dateDevolution.setPrefSize(97, 20);
                HBox.setMargin(dateDevolution, new Insets(0, 0, 0, 40));
                dateDevolution.setStyle("-fx-font-size: 16;");
                

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
        multaLabel.setText( "R$ "+multa+"");
        atrasoLabel.setText((int)dias+"");
    }

    @FXML
    public void changePageRentBook(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Books.fxml"));

        root = loader.load();

        BooksController rentController = loader.getController();

        rentController.initializeDB(db);
        rentController.setData(user);
        rentController.setLabels(user);
        rentController.init();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show(); 
    }
    @FXML
    public void changePageHome(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Home.fxml"));
        root = loader.load();

        HomeController homeController = loader.getController();
        homeController.initializeDB(db);
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        
        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void changePageDevolution(MouseEvent event) throws IOException {

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Devolucao.fxml"));

        root = loader.load();

        DevolucaoController devolucaoController = loader.getController();

        devolucaoController.initializeDB(db);
        devolucaoController.init();
        devolucaoController.setData(user);
        devolucaoController.setLabels(user);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show(); 
    }

    @FXML
    public void changePageEdit(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/EditarUser.fxml"));

        root = loader.load();

        EditarUserController editar = loader.getController();

        editar.initializeDB(db);
        editar.setData(user);
        editar.setLabels(user);

        scene = new Scene(root);
        stage = new Stage();

        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Editar Usuário");
        stage.setScene(scene);
        stage.show();
    }

    public void setLabels(){
        getRents();
        nomeLabel.setText(user.getNome());
        tipoLabel.setText(user.getTipo());
    }

    public void setData(User user){
        this.user = user;
    }

}
