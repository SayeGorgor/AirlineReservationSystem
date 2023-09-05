package com.example.airlinereservationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ARSController extends LoginPageController implements Initializable{
    ObservableList<String> options = FXCollections.observableArrayList("Atlanta", "Miami", "Chicago", "Houston",
            "New York City", "Los Angeles", "Las Vegas");
    String[] cities = {"Atlanta", "Miami", "Chicago", "Houston", "New York City", "Los Angeles",
            "Las Vegas"};
    SKGCityList cityList = new SKGCityList();
    HashMap<String, double[]> cityCoords = cityList.getCityCoords();
    double price;
    double distance;
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
    private Label usernameLabel;
    @FXML
    private Button calculatePrice;
    @FXML
    private DatePicker departDate;
    @FXML
    private DatePicker returnDate;
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        fromBox.setItems(FXCollections.observableList(options));
        toBox.setItems(FXCollections.observableList(options));
        tripBox.getItems().addAll("Round Trip", "One Way");
        tripBox.getSelectionModel().selectFirst();
        usernameLabel.setText(username);
    }
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
}
