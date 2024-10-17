package com.example.airlinereservationsystem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ARSController extends LoginPageController implements Initializable {
    //List of available cities
    ObservableList<String> options = FXCollections.observableArrayList("Atlanta", "Miami", "Chicago", "Houston",
            "New York City", "Los Angeles", "Las Vegas");
    String[] cities = {"Atlanta", "Miami", "Chicago", "Houston", "New York City", "Los Angeles",
            "Las Vegas"};
    SKGCityList cityList = new SKGCityList();
    HashMap<String, double[]> cityCoords = cityList.getCityCoords();
    double price;
    double distance;
    private Parent root;
    private final String[] accountOptionsList = {"Sign Out"};
    @FXML
    Stage stage;
    @FXML
    private ComboBox<String> fromBox;
    @FXML
    private ComboBox<String> toBox;
    @FXML
    private ComboBox<String> tripBox;
    @FXML
    private Label priceLabel;
    @FXML
    private Label errorMessage;
    @FXML
    private Hyperlink usernameLabel;
    @FXML
    private Button calculatePrice;
    @FXML
    private DatePicker departDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private ListView<String> accountOptions;

    public ARSController() throws FileNotFoundException {
    }

    //Round Trip Box selection
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        fromBox.setItems(FXCollections.observableList(options));
        toBox.setItems(FXCollections.observableList(options));
        tripBox.getItems().addAll("Round Trip", "One Way");
        tripBox.getSelectionModel().selectFirst();
        usernameLabel.setText(username);
    }
    //Calculates the price when the button is hit if all fields are full
    @FXML
    protected void onCalculatePriceButtonPress(ActionEvent event) {
        if(fromBox.getValue() == null || toBox.getValue() == null) {
            errorMessage.setText("Must select a departure and arrival location");
        } else if(fromBox.getValue() == toBox.getValue()){
            errorMessage.setText("Departure and arrival locations must be different");
        } else if(tripBox.getValue() == null) {
            errorMessage.setText("Please select ");
        } else if(returnDate.getValue().isBefore(departDate.getValue())) {
            errorMessage.setText("Return date must be after departure date");
        } else {
            distance = calculateDistance(cityCoords.get(fromBox.getValue())[1], cityCoords.get(fromBox.getValue())[0],
                    cityCoords.get(toBox.getValue())[1], cityCoords.get(toBox.getValue())[0]);
            price = distance / 5;
            if(tripBox.getValue().equals("Round Trip")) {
                price *= 2;
            }
            priceLabel.setText("$" + String.format("%.2f", price));
        }
    }
    @FXML
    protected void onUsernameLabelPress(ActionEvent event) throws IOException {
        accountOptions.setVisible(!accountOptions.isVisible());
        accountOptions.getItems().setAll(accountOptionsList);
        accountOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }
    //calculates the distance between 2 cities
    public double calculateDistance(double long1, double lat1, double long2, double lat2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(long2 - long1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
    public void displayName(String name) {
        usernameLabel.setText(name);
    }
}
