package home;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class BookController implements Initializable{

    @FXML
    private Label welcomeLabel;

    @FXML
    private TilePane tilePane;

    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button requestBtn;

    @FXML
    private Button pendingBtn;

    Dialog<ButtonType> dialog = null;
    Alert alert = new Alert(AlertType.NONE);
    
    private TextField addTitle = new TextField();
    private TextField addAuthor = new TextField();
    private TextField addYear = new TextField();
    private TextArea addDescription = new TextArea();
    private TextField addImage = new TextField();

    BookModel bookModel = new BookModel();

    @FXML
    private ImageView bookImg;

    @FXML
    private Label bookTitle;

    DataSingleTon data = DataSingleTon.getInstance();

    private String status;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status = data.getStatus();
        ArrayList<BookData> bookList = new ArrayList<BookData>(this.bookModel.getBookList());
        loadBookList(bookList);

        if(status.equals("student")){
            addBtn.setVisible(false);
            addBtn.setDisable(true);
            welcomeLabel.setText("Welcome " + data.getUser() +"!");
            pendingBtn.setVisible(false);
            pendingBtn.setDisable(true);
        } 
        else {
            welcomeLabel.setText("Welcome Librarian!");
            requestBtn.setVisible(false);
            requestBtn.setDisable(true);
        }

    }

    //Reset
    @FXML
    public void resetList() {
        tilePane.getChildren().clear();
        ArrayList<BookData> bookList = new ArrayList<BookData>(this.bookModel.getBookList());
        loadBookList(bookList);
    }

    //Search
    @FXML
    public void searchBook(){
        tilePane.getChildren().clear();
        ArrayList<BookData> bookList = new ArrayList<BookData>(this.bookModel.searchBookList(this.search.getText()));
        loadBookList(bookList);
    }

    //Add
    public void addBook(){
        createaddModal();
        dialog.showAndWait().ifPresent(response -> {
            if(response.getButtonData().equals(ButtonData.OK_DONE)){
                if (addTitle.getText().trim().isEmpty()) {addTitle.setText("No Title");}
                if (addAuthor.getText().trim().isEmpty()) {addAuthor.setText("No Author");}
                if (addYear.getText().trim().isEmpty()) {addYear.setText("0");}
                if (!isInt(addYear.getText())) {addYear.setText("0");}
                if (addDescription.getText().trim().isEmpty()) {addDescription.setText("No Description");}
                if (addImage.getText().trim().isEmpty()) {addImage.setText("placeholder.jpg");}
                bookModel.addBook(addTitle.getText().trim(), addAuthor.getText().trim(), addYear.getText().trim(), addDescription.getText().trim(), addImage.getText().trim());
            }
        });
        tilePane.getChildren().clear();
        addTitle.setText(" ");
        addAuthor.setText(" ");
        addYear.setText(" ");
        addDescription.setText(" ");
        addImage.setText(" ");
        ArrayList<BookData> bookList = new ArrayList<BookData>(this.bookModel.getBookList()); 
        loadBookList(bookList);
    }

    //Logout
    public void logOut(){
        Stage stage = (Stage) this.logoutBtn.getScene().getWindow();
        stage.close();
        Stage logInStage = new Stage();
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/login/Login.fxml")));

            logInStage.setScene(scene);
            logInStage.setTitle("Log In Page");
            logInStage.setResizable(false);
            logInStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    //Request List
    public void requestList() {
        tilePane.getChildren().clear();
        ArrayList<BookData> bookList = new ArrayList<BookData>(this.bookModel.requestBookList(data.getUser()));
        loadBookList(bookList);
    }

    //Pending List
    public void pendingList() {
        tilePane.getChildren().clear();
        ArrayList<BookData> bookList = new ArrayList<BookData>(this.bookModel.pendingBookList());
        loadBookList(bookList);
    }

    private void createaddModal(){

        dialog = new Dialog<ButtonType>();

        dialog.setTitle("Add a Book");
        ButtonType addModalBtn = new ButtonType("Add", ButtonData.OK_DONE);
        ButtonType cancelModalBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);   
        gridPane.setVgap(10);   
        gridPane.setPadding(new Insets(20, 10, 10, 10));

        addDescription.setWrapText(true);

        gridPane.add(new Label("Title"), 0, 0);
        gridPane.add(addTitle, 1, 0);
        gridPane.add(new Label("Author"), 0, 1);
        gridPane.add(addAuthor, 1, 1);
        gridPane.add(new Label("Year"), 0, 2);
        gridPane.add(addYear, 1, 2);
        gridPane.add(new Label("Description"), 0, 3);
        gridPane.add(addDescription, 1, 3);
        gridPane.add(new Label("Image"), 0, 4);
        gridPane.add(addImage, 1, 4);

        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().add(addModalBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelModalBtn);

    }

    public void loadBookList(ArrayList<BookData> bookList) {
        Image image =null;
        for (BookData bookData : bookList) {
            FileInputStream imageData = null;
            StackPane pane = new StackPane();

            try {
                imageData = new FileInputStream("../Final_Project/src/resources/images/" + bookData.getImage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                try {
                    imageData = new FileInputStream("../Final_Project/src/resources/images/placeholder.jpg");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
                image = new Image(imageData);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(350); 
                imageView.setFitWidth(240); 

                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(240);
                rectangle.setHeight(70);
                rectangle.setTranslateY(140);
                rectangle.setFill(Color.BLACK);
                rectangle.setOpacity(0.5);


                Text text = new Text(bookData.getTitle());
                // Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25);
                text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR,15));
                text.setFill(Color.WHITE);
                text.setWrappingWidth(230);
                text.setTextAlignment(TextAlignment.CENTER);
                text.setTranslateY(140);

                imageView.setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event arg0) {
                        createbookModal(bookData);
                    }
                });

                pane.getChildren().add(imageView);
                pane.getChildren().add(rectangle);
                pane.getChildren().add(text);
                tilePane.getChildren().add(pane);
        }
    }

    private void createbookModal(BookData bookData){

        data.setBookData(bookData);

        Stage stage = (Stage) search.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/book/Book.fxml"));
            stage.setTitle("SingleBook");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //check string if int
    public boolean isInt(String string) {
        int number;
        try {
            number = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
