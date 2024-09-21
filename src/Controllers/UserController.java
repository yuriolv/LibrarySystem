package Controllers;


import java.io.IOException;

import Classes.User;
import DB.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;

    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    private Label matriculaLabel;

    @FXML
    private Label nomeLabel;

    private User user;

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
    public void changeGenerateReport(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/invoiceUser.fxml"));

        root = loader.load();

        InvoiceUserController invoice = loader.getController();

        invoice.initializeDB(db);
        invoice.setData(user);
        invoice.setLabels();

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


   public void setData(User user){
       this.user=user;
   } 
   public void setLabels(User user){
        matriculaLabel.setText(user.getMatricula());
        nomeLabel.setText(user.getNome()); 
   }


}

