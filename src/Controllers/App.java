package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    private static Scene pageHome;
    private static Stage stage;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent contentHome = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
        pageHome = new Scene(contentHome);  
        
        stage = primaryStage;
        stage.setTitle("SISTEMA BIBLIOTECA");
        stage.setScene(pageHome); 
        stage.show();
   
    }
}
