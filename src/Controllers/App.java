package Controllers;

import DB.DataBase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene pageHome;
    private Stage stage;
    public DataBase db = new DataBase();
    

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Home.fxml"));
        Parent contentHome = loader.load();
        
        HomeController homeController = loader.getController();
        homeController.initializeDB(db);

        pageHome = new Scene(contentHome);  

        
        stage = primaryStage;
        stage.setTitle("SISTEMA BIBLIOTECA");
        stage.setScene(pageHome);
        stage.setResizable(false);
        stage.show();
        
    }
}
