package Controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import Classes.Book;
import Classes.RentBook;
import Classes.User;
import DB.DataBase;
import Models.Comments;
import Models.Books;
import Models.Rents;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RentBookController{

    
    @FXML
    private Label labelSair;
    @FXML
    private Label matriculaLabel;
    @FXML
    private VBox vbox;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label assuntoLabel;
    @FXML
    private Label autorLabel;
    @FXML
    private Label bookNameLabel;
    @FXML
    private ImageView capaImage;


    private User user;
    private Book selectedLivro;
    private RentBook rentBookClass;
    private Books crud;
    private ArrayList<String> comments;

    private ArrayList<Book> livros;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void changePageHome(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../Views/Home.fxml"));
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
    public void changePageBooks(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Books.fxml"));

        root = loader.load();

        BooksController rentController = loader.getController();

        rentController.setData(user);
        rentController.setLabels(user);

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
    public void rentBook(MouseEvent event, DataBase db) throws IOException{
        int num = limitarEmprestimo();
        if(selectedLivro.getQtdEstoque()==0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Livro fora de estoque");
            alert.showAndWait();
            return;

        }
        if(selectedLivro.getColeção().equals("Especial") && user.getTipo().equals("Discente")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Lamentamos informar");
            alert.setContentText("Este livro faz parte de uma coleção exclusiva para Docentes");
            alert.showAndWait();
            return;

        }
        if(user.getTipo().equals("Docente") && num==3){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Lamentamos informar");
            alert.setContentText("Limite de empréstimos simultaneos atingido");
            alert.showAndWait();
            return;
        }
        if(user.getTipo().equals("Discente") && num==2){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Lamentamos informar");
            alert.setContentText("Limite de empréstimos simultaneos atingido");
            alert.showAndWait();
            return;
        }

        Rents crudRent = new Rents();
        crud = new Books();
        int i = 0;
        livros = crud.read(db, Optional.empty());
        
        for (Book livro : livros) {
            if(livro.getTitulo().equals(selectedLivro.getTitulo())) {
                i = livros.indexOf(livro);
                break;
            }
        }

        rentBookClass = new RentBook(user.getMatricula(),  selectedLivro.getTitulo(), user.getTipo());
        rentBookClass.setDateRent();

        crudRent.create(rentBookClass);

        selectedLivro.setQtdEstoque(selectedLivro.getQtdEstoque() - 1);

        livros.set(i, selectedLivro);
        crud.update(livros);

        changePageRentReport(event);
        
        changePageBooks(event);

    }

    public void changePageRentReport(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/rentReport.fxml"));

        root = loader.load();

        RentReportController rent = loader.getController();

        rent.setLabels(user, rentBookClass);

        scene = new Scene(root);
        stage = new Stage();

        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Editar Usuário");
        stage.setScene(scene);
        stage.show();
    }

    public int limitarEmprestimo(){
        Rents crud = new Rents();
        ArrayList<RentBook> rents = new ArrayList<RentBook>();
        crud.read(rents);
        int count = 0;
        for (RentBook rent : rents) {
            if(rent.getMatricula().equals(user.getMatricula())){
                count++;
            }
        }
        return count;
    }


     public void setData(User user, Book selectedLivro){
        this.user=user;
        this.selectedLivro = selectedLivro;
    }

    public void setLabels(User user, Book livro) throws FileNotFoundException{
        matriculaLabel.setText(user.getMatricula());  
        nomeLabel.setText(user.getNome());
        bookNameLabel.setText(livro.getTitulo());
        autorLabel.setText(livro.getAutor());
        assuntoLabel.setText(livro.getAssunto());

        InputStream is = new ByteArrayInputStream(livro.getImage());
        Image img = new Image(is);

        capaImage.setImage(img);

    }

    public Label createLabel(String comment) {
        Label label = new Label();
        label.setText(comment);
        label.setPrefWidth(700);
        label.setStyle("-fx-font-family: Cambria;"+"-fx-font-size: 16;");


        return label;
    }

    public void setComments(Book livro){
        Comments crud = new Comments(); //substituir pelo crud de comentários

        comments = new ArrayList<String>(); 
        crud.read(comments, livro.getTitulo());

        for(String comment: comments) {
            Label commentLabel = createLabel(comment);
            vbox.getChildren().addAll(commentLabel);
        }
    }

}
