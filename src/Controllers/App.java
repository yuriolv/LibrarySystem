package Controllers;

import javafx.application.Application;
/* import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane; */
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene pageLogin;
    private static Scene pageAdmin;
    private static Stage stage;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /* Parent loginFxml = FXMLLoader.load(getClass().getResource("../Views/"));
        pageLogin = new Scene(loginFxml);     */

        stage = primaryStage;
        stage.setTitle("SISTEMA BIBLIOTECA");
        /* stage.setScene(); AQUI IRÁ O FXML DA PÁGINA INICIAL*/
        stage.show();
    }

    public static void changeScene(String option){

        switch(option) {

            case "pageLogin":
                stage.setScene(pageLogin);
            break;

            case "pageAdmin": 
                stage.setScene(pageAdmin);
            break;

        }
    }
}
