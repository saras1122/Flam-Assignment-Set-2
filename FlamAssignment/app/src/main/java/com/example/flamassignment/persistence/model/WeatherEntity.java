package com.example.flamassignment.persistence.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "weather")
public class WeatherEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double temperature;
    public int humidity;
    public String condition;
    public long timestamp;

    public WeatherEntity(double temperature, int humidity, String condition, long timestamp) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    //    public double getTemperature() {
    //        return temperature;
    //    }
    //
    //    public int getHumidity() {
    //        return humidity;
    //    }
}