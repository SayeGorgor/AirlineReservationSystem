package com.example.airlinereservationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.io.IOException;
import java.util.Objects;


public class CreateAccountPageController {
    private Parent root;
    String username;
    String password;
    String email;
    Boolean addAccount;
    Boolean passwordVisible = false;
    Boolean passwordCheckVisible = false;
    Image visiblePasswordImage = new Image(new FileInputStream("src/visible.jpg"));
    Image hiddenPasswordImage = new Image(new FileInputStream("src/772420-200.png"));
    @FXML
    Stage stage;
    @FXML
    private Button signUpButton;
    @FXML
    private Button backButton;
    @FXML
    private Button viewPasswordButton1;
    @FXML
    private Button viewPasswordButton2;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField passwordCheck;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField visiblePassword;
    @FXML
    private TextField visiblePasswordCheck;
    @FXML
    private Label emailErrorMessage;
    @FXML
    private Label passwordErrorMessage;
    @FXML
    private Label usernameErrorMessage;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;

    public CreateAccountPageController() throws FileNotFoundException {
    }

    //adds user info to the login database
    @FXML
    protected void onSignUpButtonPress(ActionEvent event) throws IOException {
        if(passwordVisible) {
            newPassword.setText(visiblePassword.getText());
        } else if(passwordCheckVisible) {
            passwordCheck.setText(visiblePasswordCheck.getText());
        }
        addAccount = true;
        passwordErrorMessage.setText("");
        emailErrorMessage.setText("");
        usernameErrorMessage.setText("");
        username = newUsername.getText();
        password = newPassword.getText();
        email = newEmail.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skgairlines", "root", "Fairytail#1");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from logininfo");
            while(resultSet.next()) {
                //checks to see if the email is already in the system
                if(resultSet.getString("Email").equals(email)) {
                    emailErrorMessage.setText("Account with this email already exists in system");
                    addAccount = false;
                    break;
                } else if(resultSet.getString("Username").equals(username)) {
                    emailErrorMessage.setText("This username has already been chosen");
                    addAccount = false;
                    break;
                } else if(!Objects.equals(password, passwordCheck.getText())){
                    passwordErrorMessage.setText("Passwords must match");
                    addAccount = false;
                    break;
                } else if(Objects.equals(email, "")) {
                    emailErrorMessage.setText("Must enter an email");
                    addAccount = false;
                    break;
                } else if(Objects.equals(username, "")) {
                    usernameErrorMessage.setText("Must enter a username");
                    addAccount = false;
                    break;
                } else if(Objects.equals(password, "")) {
                    passwordErrorMessage.setText("Must enter a password");
                    addAccount = false;
                    break;
                }
            }
            if(addAccount) {
                //adds user info into the database
                statement.executeUpdate("Insert into logininfo(Email, Username, Password)" +
                        " Values('" + email + "', '" + username + "', '" + password + "')");
                connection.close();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
                root = fxmlLoader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    //takes user back to the login page
    @FXML
    protected void onBackButtonPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onViewPasswordButton1Press(ActionEvent event) throws IOException {
        passwordVisible = !passwordVisible;
        if(passwordVisible) {
            visiblePassword.setText(newPassword.getText());
            visiblePassword.setVisible(true);
            newPassword.setVisible(false);
            imageView1.setImage(visiblePasswordImage);
        } else {
            newPassword.setText(visiblePassword.getText());
            newPassword.setVisible(true);
            visiblePassword.setVisible(false);
            imageView1.setImage(hiddenPasswordImage);
        }
    }
    @FXML
    protected void onViewPasswordButton2Press(ActionEvent event) throws IOException {
        passwordCheckVisible = !passwordCheckVisible;
        if(passwordCheckVisible) {
            visiblePasswordCheck.setText(passwordCheck.getText());
            visiblePasswordCheck.setVisible(true);
            passwordCheck.setVisible(false);
            imageView2.setImage(visiblePasswordImage);
        } else {
            passwordCheck.setText(visiblePasswordCheck.getText());
            passwordCheck.setVisible(true);
            visiblePasswordCheck.setVisible(false);
            imageView2.setImage(hiddenPasswordImage);
        }
    }
}
