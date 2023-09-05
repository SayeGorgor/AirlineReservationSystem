package com.example.airlinereservationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LoginPage extends Application {
    HashMap<String, String> loginInfo = new HashMap<String, String>();
    UsernamesAndPasswords logins = new UsernamesAndPasswords();
    public void setLoginInfo(HashMap<String, String> loginInfo) {
        this.loginInfo = loginInfo;
    }
    /*LoginPage(HashMap<String, String> loginInfoOriginal) throws Exception{
        loginInfo = loginInfoOriginal;
        load();
    }
     */

    @Override
    public void start(Stage stage) throws IOException {
        UsernamesAndPasswords logins = new UsernamesAndPasswords();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        loginInfo = logins.getLoginInfo();

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SKG Airlines");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
