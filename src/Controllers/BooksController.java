package Controllers;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.paint.Color;

import Classes.Book;
import Classes.User;
import DB.DataBase;
import Models.Books;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;

public class BooksController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataBase db;
    
    public void initializeDB(DataBase db){
        this.db = db;
    }

    @FXML
    private Label nomeLabel;
    @FXML
    private Label tipoLabel;
    @FXML
    private FlowPane flowPane;
    @FXML
    private TextField filtroTextField;

    
    BlurType threePassBox = BlurType.THREE_PASS_BOX;
    private  DropShadow dropShadow = new DropShadow(threePassBox, Color.BLACK, 10, 0, 0, 0);

    private User user;
    private Book selectedLivro;

    private ArrayList<Book> livros;

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
    void changePageHome(MouseEvent event) throws IOException{
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
    void changePageBook(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/book.fxml"));
        root = loader.load();

        RentBookController rentBookController = loader.getController();
        rentBookController.initializeDB(db);

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        if(stage.isMaximized() == true){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, screenSize.getMaxX(), screenSize.getMaxY());
            stage.setMaximized(true);
        } else {
            scene = new Scene(root);
        }

        rentBookController.setData(user, selectedLivro);
        rentBookController.setLabels(user, selectedLivro);
        rentBookController.setComments(selectedLivro);

        stage.setScene(scene);
        stage.show();
    }


    public void setData(User user){
        this.user=user;
    }

    public void setLabels(User user){
        nomeLabel.setText(user.getNome()); 
        tipoLabel.setText(user.getTipo());   
    }

    public AnchorPane createAnchorPane(Book livro) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(179);
        anchorPane.setPrefHeight(241);
        anchorPane.setStyle(
            "-fx-background-color: white;"
            + "-fx-background-radius: 10;"
            + "-fx-border-radius: 10;"
            +"-fx-cursor:HAND;"
            + "-fx-border-color:  #e2dddf;"
            );
        
        try {
            InputStream is = new ByteArrayInputStream(livro.getImage());
            Image img = new Image(is);
            ImageView image = new ImageView(img);

            image.setFitHeight(142);
            image.setFitWidth(97);

            image.setLayoutX(41);
            image.setLayoutY(14);

            Label name = new Label(livro.getTitulo());
            Label author = new Label(livro.getAutor());
            Label subject = new Label(livro.getAssunto());

            
            name.setStyle("-fx-font-weight: bold;"+"-fx-font-size: 12;");
            author.setStyle("-fx-font-style: italic;"+"-fx-font-size: 14;");
            subject.setStyle("-fx-font-size: 14;");

            name.setLayoutX(14);
            name.setLayoutY(167);

            author.setLayoutX(14);
            author.setLayoutY(184);

            subject.setLayoutX(14);
            subject.setLayoutY(204);
    
            anchorPane.getChildren().addAll(name, author, subject, image);
        } catch (Exception e) {
            System.out.println(e);
        }

        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
               try   {
                selectedLivro = livro;
                changePageBook(arg0);
               } catch(Exception e) {
                System.out.println(e);
               }
            }
            
        });
        return anchorPane;
    }
    @FXML
    public void changePageDevolution(MouseEvent event) throws IOException {

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Devolucao.fxml"));

        root = loader.load();

        DevolucaoController devolucaoController = loader.getController();

        devolucaoController.initializeDB(db);
        devolucaoController.init();
        devolucaoController.setData(user);
        devolucaoController.setLabels(user);

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


    public void loadBooks() {
        Books crud_Books = new Books();
        livros = crud_Books.read(db, Optional.empty(), Optional.empty());
        
        for (Book livro : livros) {
            AnchorPane bookCard = createAnchorPane(livro);
            flowPane.getChildren().add(bookCard);
        }
       
    }

    @FXML
    public void pesquisarLivros(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            String filtro = filtroTextField.getText();
                
            flowPane.getChildren().clear();
            ArrayList<String> like = new ArrayList<String>();
            like.add("UPPER(autor) LIKE UPPER('%" + filtro + "%')");
            like.add("UPPER(titulo) LIKE UPPER('%" + filtro + "%')");
            like.add("UPPER(assunto) LIKE UPPER('%" + filtro + "%')");
            like.add("UPPER(colecao) LIKE UPPER('%" + filtro + "%')");
    
            Books crud_Books = new Books();
            Optional<ArrayList<String>> conditions = Optional.of(like);
    
    
            livros = crud_Books.read(db, conditions, Optional.of(" OR "));
            
            if (livros == null) {
                return;
            }

            for (Book livro : livros) {
                AnchorPane bookCard = createAnchorPane(livro);
                flowPane.getChildren().add(bookCard);
            }
        }

       
    }
    
    @FXML
    public void pesquisarLivros2(MouseEvent event) {
        String filtro = filtroTextField.getText();
            
        flowPane.getChildren().clear();
        ArrayList<String> like = new ArrayList<String>();
        like.add("UPPER(autor) LIKE UPPER('%" + filtro + "%')");
        like.add("UPPER(titulo) LIKE UPPER('%" + filtro + "%')");
        like.add("UPPER(assunto) LIKE UPPER('%" + filtro + "%')");
        like.add("UPPER(colecao) LIKE UPPER('%" + filtro + "%')");

        Books crud_Books = new Books();
        Optional<ArrayList<String>> conditions = Optional.of(like);


        livros = crud_Books.read(db, conditions, Optional.of(" OR "));

        if (livros == null) {
            return;
        }
        
        for (Book livro : livros) {
            AnchorPane bookCard = createAnchorPane(livro);
            flowPane.getChildren().add(bookCard);
        }
    }

    public void init() {
        loadBooks();
    }

}