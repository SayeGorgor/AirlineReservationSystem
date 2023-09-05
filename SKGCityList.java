package com.example.airlinereservationsystem;

import java.util.HashMap;

public class SKGCityList {
    HashMap<String, double[]> cityCoords = new HashMap<String, double[]>();
    SKGCityList() {
        cityCoords.put("Atlanta", new double[]{33.749, -84.38798});
        cityCoords.put("Miami", new double[]{25.77427, -80.19366});
        cityCoords.put("Los Angeles", new double[]{34.05223, -118.24368});
        cityCoords.put("Chicago", new double[]{41.85003, -87.65005});
        cityCoords.put("New York City", new double[]{40.71427, -74.00597});
        cityCoords.put("Houston", new double[]{29.76328, -95.36327});
        cityCoords.put("Las Vegas", new double[]{36.17497, -115.13722});
    }
    protected HashMap<String, double[]> getCityCoords() {
        return cityCoords;
    }
}
