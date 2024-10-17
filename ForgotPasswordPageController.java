package com.example.airlinereservationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ForgotPasswordPageController {
    private Parent root;
    String username;
    String password;
    @FXML
    Stage stage;
    @FXML
    private Button continueButton;
    @FXML
    private Button backButton;
    @FXML
    private Label responseText;
    @FXML
    private TextField usernameField;

    public void onContinueButtonPress(ActionEvent event) throws IOException {
        username = usernameField.getText();
        boolean userFound = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skgairlines", "root", "Fairytail#1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from logininfo");
            while(resultSet.next()) {
                if(resultSet.getString("Username").equals(username)) {
                    userFound = true;
                    password = resultSet.getString("Password");
                    break;
                }
            }
            if(userFound) {
                responseText.setText("Your password is " + password);
            } else {
                responseText.setText("That username was not found in our system, please try again");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onBackButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
