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

import java.io.IOException;

import java.sql.*;
import java.util.Objects;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

public class ForgotUsernamePageController {
    private Parent root;
    String email;
    String username;
    Boolean sendEmail = false;
    SMTPAuthenticator authenticator = new SMTPAuthenticator();
    @FXML
    Stage stage;
    @FXML
    private Button continueButton;
    @FXML
    private Button backButton;
    @FXML
    private Label responseText;
    @FXML
    private TextField emailField;

    public void onContinueButtonPress(ActionEvent event) throws IOException {
        email = emailField.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skgairlines", "root", "Fairytail#1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from logininfo");
            while(resultSet.next()) {
                if(resultSet.getString("Email").equals(email)) {
                    sendEmail = true;
                    username = resultSet.getString("Username");
                    break;
                }
            }
            if(sendEmail) {
                responseText.setText("An email containing your username is being sent to your email!");
                String recipient = email;
                String sender = "skgairlines@gmail.com";
                String host = "smtp.gmail.com";
                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", host);
                properties.setProperty("mail.smtp.port", "587");
                properties.setProperty("mail.smtp.starttls.enable", "true");
                properties.setProperty("mail.smtp.auth", "true");
                properties.setProperty("mail.smtp.password", "tlhclpsuoqszcyqo");
                Session session = Session.getDefaultInstance(properties, authenticator);
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(sender));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                    message.setSubject("Forgot Username");
                    message.setText("Your username is: " + username);
                    Transport.send(message);
                } catch(MessagingException mex) {
                    mex.printStackTrace();
                }
            } else {
                responseText.setText("This email was not found in our system, please enter another email.");
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
