package com.example.airlinereservationsystem;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPageController {
    private Parent root;
    String username;
    String password;
    Boolean passwordVisible = false;
    Image visiblePasswordImage = new Image(new FileInputStream("src/visible.jpg"));
    Image hiddenPasswordImage = new Image(new FileInputStream("src/772420-200.png"));
    @FXML
    private Stage stage;
    @FXML
    protected TextField usernameField;
    @FXML
    protected TextField visiblePassword;
    @FXML
    private ImageView imageView;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label incorrectLogin;
    @FXML
    private Button loginButton;
    @FXML
    private Button viewPasswordButton;
    @FXML
    private Hyperlink createAccount;
    @FXML
    private Hyperlink forgotUsername;
    @FXML
    private Hyperlink forgotPassword;

    public LoginPageController() throws FileNotFoundException {
    }

    @FXML
    protected void onLoginButtonPress(ActionEvent event) throws IOException {
        username = String.valueOf(usernameField.getText());
        password = String.valueOf(passwordField.getText());
        //checks for username and password in the database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skgairlines", "root", "Fairytail#1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from logininfo");
            while (resultSet.next()) {
                if (resultSet.getString("Username").equals(username)) {
                    if (resultSet.getString("Password").equals(password)) {
                        connection.close();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                        root = fxmlLoader.load();
                        ARSController arsController = fxmlLoader.getController();
                        arsController.displayName(username);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onCreateAccountLinkPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-account-page.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onForgotUsernameLinkPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgot-username-page.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onForgotPasswordLinkPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgot-password-page.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onViewPasswordButtonPress(ActionEvent event) throws IOException {
        passwordVisible = !passwordVisible;
        if(passwordVisible) {
            visiblePassword.setText(passwordField.getText());
            visiblePassword.setVisible(true);
            passwordField.setVisible(false);
            imageView.setImage(visiblePasswordImage);
        } else {
            passwordField.setText(visiblePassword.getText());
            passwordField.setVisible(true);
            visiblePassword.setVisible(false);
            imageView.setImage(hiddenPasswordImage);
        }
    }
}
