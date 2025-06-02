package com.example.flamassignment.network.model;

public class WeatherResponse {
    public double temperature;
    public int humidity;
    public String condition;

    public WeatherResponse(double temperature, int humidity, String condition) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.condition = condition;
    }

//    public double getTemperature() {
//        return temperature;
//    }
//
//    public int getHumidity() {
//        return humidity;
//    }
}