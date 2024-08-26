package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


import Classes.RentBook;
import Classes.User;
import DB.DataBase;
import Models.Rents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DevolucaoController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    private TableColumn<RentBook, String> dataColumn;

    @FXML
    private TableColumn<RentBook, String> nomeColumn;

    @FXML
    private TableView<RentBook> tableRent;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label matriculaLabel;

    private User user;

    private ArrayList<RentBook> rents;

    private ObservableList<RentBook> rentsObs;


    @FXML
    void changePageUser(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/User.fxml"));

        root = loader.load();

        UserController userController = loader.getController();

        userController.initializeDB(db);
        userController.setData(user);
        userController.setLabels(user);

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
    public void devolverLivro(MouseEvent event) throws IOException {
        Rents crud = new Rents();
        int i = tableRent.getSelectionModel().getSelectedIndex();
        ArrayList<String> conditions = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        LocalDate lc_data_devolucao;
        LocalDate lc_data_aluguel;

        try{

            for (RentBook rent : rents) {
                String data_aluguel = rent.getDateRent();
                int id_livro = rent.getId_livro();
                if(rentsObs.get(i).getDateRent().equals(data_aluguel) &&
                rentsObs.get(i).getId_livro() == id_livro){
                    conditions.add(String.format("id_livro = %s", id_livro));

                    lc_data_aluguel = LocalDate.parse(data_aluguel, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    System.out.println(data_aluguel);
                    data_aluguel = lc_data_aluguel.format(DateTimeFormatter.ofPattern("YYYYMMDD"));
                    System.out.println(data_aluguel);
                    
                    conditions.add(String.format("data_aluguel = \'%s\'", data_aluguel));
                    crud.delete(db, conditions);//consertar delete

                    changePageComentarios(event, rent);
                }
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Ocorreu um erro");
        }
            
    }

    public void changePageComentarios(MouseEvent event, RentBook rent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Comentario.fxml"));

        root = loader.load();

        ComentariosController comentario = loader.getController();
        

        comentario.initializeDB(db);
        comentario.setData(rent);

        scene = new Scene(root);
        stage = new Stage();

        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Comentário");
        stage.setScene(scene);
        stage.show();



    }
    public void init() {
        Rents crud = new Rents();
        rents = crud.read(db, Optional.empty()); 

        rentsObs = FXCollections.observableArrayList(rents);

        dataColumn.setCellValueFactory(new PropertyValueFactory<>("DateRent"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Matricula"));

        tableRent.setItems(rentsObs);
    }

    public void setData(User user) {
        this.user = user;
    }
     public void setLabels(User user){
        matriculaLabel.setText(user.getMatricula());
        nomeLabel.setText(user.getNome()); 
   }
}
