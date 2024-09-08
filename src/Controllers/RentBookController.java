package Controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

import Classes.Book;
import Classes.Comment;
import Classes.RentBook;
import Classes.User;
import DB.DataBase;
import Models.Comments;
import Models.Books;
import Models.Rents;
import Models.Users;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.text.html.ImageView;

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
    private ArrayList<Comment> comments;

    private ArrayList<Book> livros;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    public void changePageHome(MouseEvent event) throws IOException{
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
    public void changePageBooks(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Books.fxml"));

        root = loader.load();

        BooksController booksController = loader.getController();

        booksController.initializeDB(db);
        booksController.init();
        booksController.setData(user);
        booksController.setLabels(user);

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
    public void rentBook(MouseEvent event) throws IOException{
        int num = limitarEmprestimo();
        ArrayList<String> condition = new ArrayList<>();
        Optional<ArrayList<String>> conditions = Optional.of(condition);
        String titulo;

        crud = new Books();
        
        titulo = "'" + selectedLivro.getTitulo() + "'";
        condition.add("titulo = "+ titulo);
        livros = crud.read(db, conditions, Optional.of(" AND "));


        if(livros.get(0).getQtdEstoque()==0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Livro fora de estoque");
            alert.showAndWait();
            return;

        }
        if(livros.get(0).getColeção().equals("Especial") && user.getTipo().equals("Discente")){
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

        rentBookClass = new RentBook(user.getMatricula(),  selectedLivro.getID(), user.getTipo());
        rentBookClass.setDateRent();

        crudRent.create(rentBookClass, db);
        selectedLivro.setQtdEstoque(selectedLivro.getQtdEstoque()-1);


        crud.update(db, selectedLivro, Optional.of(condition) );

        changePageRentReport(event);
        
        changePageBooks(event);

    }

    public void changePageRentReport(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/rentReport.fxml"));

        root = loader.load();
        
        RentReportController rent = loader.getController();

        rent.initializeDB(db);
        rent.setData(selectedLivro);
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
        rents = crud.read(db, Optional.empty()); //finalizar este método


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

    public Label createLabel(String user) {
        Label label = new Label();
        label.setText(user);
        label.setPrefWidth(454);
        label.setPrefHeight(28);
        label.setFont(Font.font("System", FontWeight.BOLD, 11));
        label.setTextFill(Color.web("#656565"));
        return label;
    }

    public void setComments(Book livro){
        Comments crud = new Comments(); //substituir pelo crud de comentários
        Users crud_users = new Users();
        ArrayList<String> conditions = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        conditions.add("id_livro = " + "'"+ livro.getID() + "'");

        comments = crud.read(db, Optional.of(conditions));

        
        if (!comments.isEmpty()){
            for(Comment comment: comments) {
                conditions.clear();
                conditions.add(String.format("matricula = '%s'", comment.getMatricula_usuario()));
                users = crud_users.read(db, Optional.of(conditions));
        
                VBox vBox = new VBox();
                HBox hbox = new HBox();
                Text text = new Text();
                Image file = new Image("file:src/Views/Assets/userIcon.png");
                ImageView avatar = new ImageView(file);
    
                text.setText(comment.getComentario());
                text.setWrappingWidth(541);
    
                vBox.setPrefWidth(700);
                vBox.setMargin(text, new Insets(5, 0, 0, 70));
                
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPrefHeight(38);
                hbox.setPrefWidth(700);
                hbox.setPadding(new Insets(0, 20, 0, 20));
                hbox.setSpacing(7);
    
                avatar.setFitWidth(34);
                avatar.setFitHeight(34);
    
    
                hbox.getChildren().addAll(avatar);
                hbox.getChildren().addAll(createLabel(users.get(0).getNome()));
    
                vBox.getChildren().addAll(hbox);
                vBox.getChildren().addAll(text);
    
                vbox.getChildren().addAll(vBox);
            }
        }else {
            Label result = createLabel("Ainda não há nenhuma avaliação para este livro");

            result.setPrefWidth(342);
            result.setFont(javafx.scene.text.Font.font("System", FontWeight.NORMAL, 16));
            result.setTextFill(Color.web("#00000099"));

            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(result);
        }
    }

}
