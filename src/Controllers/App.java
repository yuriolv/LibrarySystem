package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene pageHome;
    private static Scene loginUser;
    private static Scene loginAdmin;
    //private static Scene pageAdmin;
    private static Scene pageAcervo;
    private static Stage stage;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

        Parent contentTelaInicial = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
        pageHome = new Scene(contentTelaInicial);  

        Parent contentUserLogin = FXMLLoader.load(getClass().getResource("../Views/loginUser.fxml"));
        loginUser = new Scene(contentUserLogin); 

        Parent contentTelaAcervo = FXMLLoader.load(getClass().getResource("../Views/Acervo.fxml"));
        pageAcervo = new Scene(contentTelaAcervo);

        Parent contentLoginAdmin = FXMLLoader.load(getClass().getResource("../Views/loginAdmin.fxml"));
        loginAdmin = new Scene(contentLoginAdmin);

        stage = primaryStage;
        stage.setTitle("SISTEMA BIBLIOTECA");
        stage.setScene(pageHome); 
        stage.show();
    } catch (Exception e) {
        System.out.println(e);
    }
}
    public static void changeScene(String option){

        switch(option) {

            case "loginUser":
                stage.setScene(loginUser);
            break;

            case "loginAdmin": 
                stage.setScene(loginAdmin);
                break;

            case "pageAcervo":
                stage.setScene(pageAcervo);
                break;
            case "pageHome":
                stage.setScene(pageHome);
                break;

        }
    }
}
