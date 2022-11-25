package book;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import home.BookData;
import home.DataSingleTon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SingleBookController implements Initializable{
    @FXML
    private ImageView bookImg;

    @FXML
    private Label bookTitle;
    @FXML
    private Label bookAuthor;
    @FXML
    private Label bookYear;
    @FXML
    private Label bookDescription;
    @FXML
    private Label statusLabel;

    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML 
    private Button requestbtn;
    @FXML
    private Button approveBtn;
    @FXML
    private Button denyBtn;
    @FXML
    private Button takenBtn;
    @FXML
    private Button availableBtn;

    private BookData bookData;

    @FXML
    private Label userLabel;
    @FXML
    private Label requestedbyLabel;

    DataSingleTon data = DataSingleTon.getInstance();

    SingleBookModel singleBookModel = new SingleBookModel();

    Dialog<ButtonType> dialog = null;
    Alert alert = new Alert(AlertType.NONE);
    
    private TextField editTitle = new TextField();
    private TextField editAuthor = new TextField();
    private TextField editYear = new TextField();
    private TextArea editDescription = new TextArea();
    private TextField editImage = new TextField();

    private String status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status = data.getStatus();
        bookData = data.getBookData();
        bookTitle.setText(bookData.getTitle());
        bookAuthor.setText(bookData.getAuthor());
        bookYear.setText(Integer.toString(bookData.getYear()));
        bookDescription.setText(bookData.getDescription());
        try {
            Image image = new Image(new FileInputStream("../Final_Project/src/resources/images/" + bookData.getImage()));
            bookImg.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       
        availableBtn.setDisable(true);
        availableBtn.setVisible(false);
        takenBtn.setDisable(true);
        takenBtn.setVisible(false);
        statusLabel.setText(bookData.getAvailability());

        requestbtn.setText(bookData.getStatus());

        if(bookData.getStatus().equals("request")) {
        }
        else {
            requestbtn.setDisable(true);
        }

        if(bookData.getStatus().equals("pending")) {
            approveBtn.setDisable(false);
            approveBtn.setVisible(true);
            denyBtn.setDisable(false);
            denyBtn.setVisible(true);
        }
        else {
            approveBtn.setDisable(true);
            approveBtn.setVisible(false);
            denyBtn.setDisable(true);
            denyBtn.setVisible(false);
        }

        if(bookData.getStatus().equals("pickup")) {
            takenBtn.setDisable(false);
            takenBtn.setVisible(true);
        }

        if(bookData.getStatus().equals("return")) {
            availableBtn.setDisable(false);
            availableBtn.setVisible(true);
        }

        if(statusLabel.getText().equals("unavailable")) {
            userLabel.setText(bookData.getStudent());
            requestedbyLabel.setText("Book Requested by: ");
        }

        if(status.equals("student")){
            approveBtn.setDisable(true);
            approveBtn.setVisible(false);
            denyBtn.setDisable(true);
            denyBtn.setVisible(false);
            takenBtn.setDisable(true);
            takenBtn.setVisible(false);
            availableBtn.setDisable(true);
            availableBtn.setVisible(false);
            deleteBtn.setVisible(false);
            deleteBtn.setDisable(true);
            editBtn.setVisible(false);
            editBtn.setDisable(true);
            userLabel.setText("");
            requestedbyLabel.setText("");

        }

    }

    //Delete
    @FXML
    public void deleteBook(){
        singleBookModel.delete(bookData.getTitle());
        back();
    }

    //Edit
    @FXML
    public void editBook(){
        createModal();

        dialog.showAndWait().ifPresent(response -> {
            if(response.getButtonData().equals(ButtonData.OK_DONE)){
                if (editTitle.getText().trim().isEmpty()) {editTitle.setText("No Title");}
                if (editAuthor.getText().trim().isEmpty()) {editAuthor.setText("No Author");}
                if (editYear.getText().trim().isEmpty()) {editYear.setText("0");}
                if (!isInt(editYear.getText().trim())) {editYear.setText("0");}
                if (editDescription.getText().trim().isEmpty()) {editDescription.setText("No Description");}
                if (editImage.getText().trim().isEmpty()) {editImage.setText("placeholder.jpg");}
                singleBookModel.edit(bookData.getTitle().trim(), bookData.getImage().trim(), editTitle.getText().trim(), editAuthor.getText().trim(), editYear.getText().trim(), editDescription.getText().trim(), editImage.getText().trim());
                bookTitle.setText(editTitle.getText().trim());
                bookAuthor.setText(editAuthor.getText().trim());
                bookYear.setText(editYear.getText().trim());
                bookDescription.setText(editDescription.getText().trim());
                try {
                    Image image = new Image(new FileInputStream("../Final_Project/src/resources/images/" + editImage.getText().trim()));
                    bookImg.setImage(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Back
    @FXML
    public void back(){
        Stage homeStage = (Stage) this.bookTitle.getScene().getWindow();
        homeStage.close();
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/home/Library.fxml")));

            homeStage.setScene(scene);
            homeStage.setTitle("Home Page");
            homeStage.setResizable(false);
            homeStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Request
    @FXML
    public void requestBook(){
        singleBookModel.request(data.getUser(),bookData.getTitle(), bookData.getImage());
        statusLabel.setText("unavailable");
        requestbtn.setText("Pending");
        requestbtn.setDisable(true);
    }

    //Approve
    @FXML
    public void approveRequest() {
        singleBookModel.approve(bookData.getTitle(), bookData.getImage());
        requestbtn.setText("pickup");
        approveBtn.setDisable(true);
        approveBtn.setVisible(false);
        denyBtn.setDisable(true);
        denyBtn.setVisible(false);
        takenBtn.setDisable(false);
        takenBtn.setVisible(true);
    }

    //Deny
    @FXML
    public void denyRequest() {
        singleBookModel.deny(bookData.getTitle(), bookData.getImage());
        statusLabel.setText("available");
        requestbtn.setText("request");
        requestbtn.setDisable(false);
        requestbtn.setVisible(true);
        approveBtn.setDisable(true);
        approveBtn.setVisible(false);
        denyBtn.setDisable(true);
        denyBtn.setVisible(false);
    }

    //Taken
    @FXML
    public void takenBook() {
        singleBookModel.taken(bookData.getTitle(), bookData.getImage());
        requestbtn.setText("return");
        takenBtn.setDisable(true);
        takenBtn.setVisible(false);
        availableBtn.setDisable(false);
        availableBtn.setVisible(true);
    }

    //Available
    @FXML
    public void availableBook() {
        singleBookModel.available(bookData.getTitle(), bookData.getImage());
        statusLabel.setText("available");
        requestbtn.setText("request");
        requestbtn.setDisable(false);
        requestbtn.setVisible(true);
        availableBtn.setDisable(true);
        availableBtn.setVisible(false);
    }

    private void createModal(){

        dialog = new Dialog<ButtonType>();

        dialog.setTitle("Edit a Book");
        ButtonType editModalBtn = new ButtonType("Edit", ButtonData.OK_DONE);
        ButtonType cancelModalBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);   
        gridPane.setVgap(10);   
        gridPane.setPadding(new Insets(20, 10, 10, 10));

        editDescription.setWrapText(true);

        editTitle.setText(bookData.getTitle());
        editAuthor.setText(bookData.getAuthor());
        editYear.setText(Integer.toString(bookData.getYear()));
        editDescription.setText(bookData.getDescription());
        editImage.setText(bookData.getImage());

        gridPane.add(new Label("Title"), 0, 0);
        gridPane.add(editTitle, 1, 0);
        gridPane.add(new Label("Author"), 0, 1);
        gridPane.add(editAuthor, 1, 1);
        gridPane.add(new Label("Year"), 0, 2);
        gridPane.add(editYear, 1, 2);
        gridPane.add(new Label("Description"), 0, 3);
        gridPane.add(editDescription, 1, 3);
        gridPane.add(new Label("Image"), 0, 4);
        gridPane.add(editImage, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().add(editModalBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelModalBtn);
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
