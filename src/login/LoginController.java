package login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import home.DataSingleTon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{

    LoginModel loginModel = new LoginModel();

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Label loginStatus;

    DataSingleTon data = DataSingleTon.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    public void Login(ActionEvent event){

        if(this.loginModel.isLogin(this.username.getText(), this.password.getText())){
            // this.loginStatus.setText("You have successfully logged in!");

            Stage stage = (Stage) this.loginBtn.getScene().getWindow();
            stage.close();

            homePage();
        }else{
            this.loginStatus.setText("Wrong Credentials");
        }
    }

    public void homePage(){
        data.setStatus(this.loginModel.checkStatus(this.username.getText(), this.password.getText()));
        data.setUser(this.username.getText());
        Stage homeStage = new Stage();
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
    
}
