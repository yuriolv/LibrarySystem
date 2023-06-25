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
    private static Scene pageAdmin;
    private static Scene pageAdminUsers;
    private static Scene pageAdminBooks;
    private static Scene pageAcervo;
    private static Scene pageUser;

    private static Stage stage;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

        Parent contentHome = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
        pageHome = new Scene(contentHome);  
            
        Parent contentUserLogin = FXMLLoader.load(getClass().getResource("../Views/loginUser.fxml"));
        loginUser = new Scene(contentUserLogin); 

        Parent contentAcervo = FXMLLoader.load(getClass().getResource("../Views/Acervo.fxml"));
        pageAcervo = new Scene(contentAcervo);

        Parent contentLoginAdmin = FXMLLoader.load(getClass().getResource("../Views/loginAdmin.fxml"));
        loginAdmin = new Scene(contentLoginAdmin);
        
        Parent contentUser = FXMLLoader.load(getClass().getResource("../Views/User.fxml"));
        pageUser = new Scene(contentUser);
        
        Parent contentAdmin = FXMLLoader.load(getClass().getResource("../Views/Admin.fxml"));
        pageAdmin = new Scene(contentAdmin);

       /*  Parent contentAdminUsers = FXMLLoader.load(getClass().getResource("../Views/AdminUsers.fxml"));
        pageAdminUsers = new Scene(contentAdminUsers);

        Parent contentAdminBooks = FXMLLoader.load(getClass().getResource("../Views/AdminBooks.fxml"));
        pageAdminBooks = new Scene(contentAdminBooks); */

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

            case "pageHome":
                stage.setScene(pageHome);
                break;
                
            case "loginUser":
                stage.setScene(loginUser);
            break;

            case "loginAdmin": 
                stage.setScene(loginAdmin);
                break;

            case "pageAcervo":
                stage.setScene(pageAcervo);
                break;

            case "pageUser":
                stage.setScene(pageUser);
                break;
           
            case "pageAdmin":
                stage.setScene(pageAdmin);
                break;

            case "pageAdminUsers":
                stage.setScene(pageAdmin);
                break;
            case "pageAdminBooks":
                stage.setScene(pageAdmin);
                break;

        }
    }
}
