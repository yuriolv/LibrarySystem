package Controllers;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;



import Classes.RentBook;
import Classes.User;
import Classes.Book;
import DB.DataBase;
import Models.Books;
import Models.Rents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

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
    private Label tipoLabel;
    @FXML
    private Label matriculaLabel;

    private User user;

    private ArrayList<RentBook> rents;

    private ObservableList<RentBook> rentsObs;


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
    public void devolverLivro(MouseEvent event) throws IOException {
        Rents crud = new Rents();
        Books crud_livro = new Books();
        int i = tableRent.getSelectionModel().getSelectedIndex();
        ArrayList<String> conditions = new ArrayList<>();

        LocalDate lc_data_aluguel;

        try{            
            for (RentBook rent : rents) {
                String data_aluguel = rent.getDateRent();
                int id = rent.getId();
                int id_livro = rent.getId_livro();
                if(rentsObs.get(i).getId() == id){
                    conditions.add(String.format("\"ID\" = %s", id));

                    lc_data_aluguel = LocalDate.parse(data_aluguel, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    data_aluguel = lc_data_aluguel.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                    crud.delete(db, conditions);//consertar delete

                    conditions.clear();
                    conditions.add("\"ID\" =" + id_livro);

                    ArrayList<Book> livros = crud_livro.read(db, Optional.of(conditions), Optional.of(" AND "));
                    
                    livros.get(0).setQtdEstoque(livros.get(0).getQtdEstoque()+1);
                    crud_livro.update(db, livros.get(0), Optional.of(conditions));

                    changePageComentarios(event, rent);
                    rentsObs.remove(i);
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

        stage.setResizable(false);
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

        stage.setResizable(false);
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
        nomeLabel.setText(user.getNome()); 
        tipoLabel.setText(user.getTipo());   
    }
}
