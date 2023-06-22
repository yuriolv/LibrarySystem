package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene pageLogin;
    private static Scene pageAdmin;
    private static Scene pageAcervo;
    private static Scene pageInitial;
    private static Stage stage;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

        Parent contentTelaInicial = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
        pageInitial = new Scene(contentTelaInicial);  

        Parent contentTelaLogin = FXMLLoader.load(getClass().getResource("../Views/Login.fxml"));
        pageLogin = new Scene(contentTelaLogin); 

        Parent contentTelaAcervo = FXMLLoader.load(getClass().getResource("../Views/Acervo.fxml"));
        pageAcervo = new Scene(contentTelaAcervo);
        

        stage = primaryStage;
        stage.setTitle("SISTEMA BIBLIOTECA");
        stage.setScene(pageInitial); 
        stage.show();
    } catch (Exception e) {
        System.out.println(e);
    }
}
    public static void changeScene(String option){

        switch(option) {

            case "pageLogin":
                stage.setScene(pageLogin);
            break;

            case "pageAdmin": 
                stage.setScene(pageAdmin);
                break;

            case "pageAcervo":
                stage.setScene(pageAcervo);

            break;

        }
    }
}
