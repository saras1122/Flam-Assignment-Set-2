package com.example.flamassignment.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.flamassignment.persistence.model.WeatherEntity;
import java.util.List;

@Dao
public interface WeatherDao {
    @Insert
    void insert(WeatherEntity weather);

    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    LiveData<WeatherEntity> getLatestWeather();

    @Query("SELECT * FROM weather WHERE timestamp >= :startDate ORDER BY timestamp ASC")
    LiveData<List<WeatherEntity>> getWeeklyWeather(long startDate);

    @Query("DELETE FROM weather")
    void deleteAll();
}