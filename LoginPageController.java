package com.example.airlinereservationsystem;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LoginPageController extends LoginPage{
    private AnchorPane rootPane;
    String username;
    String password;
    @FXML
    private Stage stage;
    @FXML
    protected TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label incorrectLogin;
    @FXML
    private Button loginButton;
    @FXML
    protected void onLoginButtonPress(ActionEvent event) throws IOException {
        setLoginInfo(logins.getLoginInfo());
        username = String.valueOf(usernameField.getText());
        password = String.valueOf(passwordField.getText());
        if(loginInfo.containsKey(username)) {
            if(loginInfo.get(username).equals(password)) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                //stage.setTitle("SKG Airlines");
                stage.setScene(scene);
                stage.show();
            } else {
                incorrectLogin.setText("Incorrect Password");
            }
        } else {
            incorrectLogin.setText("Incorrect Username");
        }
    }



    public void setLoginInfo(HashMap<String, String> logins) {
        loginInfo = logins;
    }
}
