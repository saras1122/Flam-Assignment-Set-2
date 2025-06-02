package com.example.flamassignment.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.flamassignment.network.ApiClient;
import com.example.flamassignment.network.ApiService;
import com.example.flamassignment.network.model.WeatherResponse;
import com.example.flamassignment.persistence.AppDatabase;
import com.example.flamassignment.persistence.WeatherDao;
import com.example.flamassignment.persistence.model.WeatherEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

public class WeatherRepository {
    private final WeatherDao weatherDao;
    private final ApiService apiService;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public WeatherRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        weatherDao = db.weatherDao();
        apiService = ApiClient.getApiService();
    }

    public LiveData<WeatherEntity> getLatestWeather() {
        return weatherDao.getLatestWeather();
    }

    public LiveData<List<WeatherEntity>> getWeeklyWeather() {
        long oneWeekAgo = new Date().getTime() - (7 * 24 * 60 * 60 * 1000);
        Log.d("time", oneWeekAgo+"");
        return weatherDao.getWeeklyWeather(oneWeekAgo);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchAndSaveWeather() {
        executor.execute(() -> {
            try {
                Response<WeatherResponse> response = apiService.getCurrentWeather().execute();

                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    WeatherEntity entity = new WeatherEntity(
                            weather.temperature,
                            weather.humidity,
                            weather.condition,
                            new Date().getTime()
                    );
                    weatherDao.insert(entity);
                    errorMessage.postValue(null);
                    Log.d("WeatherRepo", "Weather data saved: " + entity.temperature);
                } else {
                    String error = "Server error: " + response.code();
                    errorMessage.postValue(error);
                    Log.e("WeatherRepo", error);
                }
            } catch (Exception e) {
                String error = "Network error: " + e.getMessage();
                errorMessage.postValue(error);
                Log.e("WeatherRepo", error, e);
            }
        });
    }
    public void fetchAndSaveWeatherSync() {
        try {
            Response<WeatherResponse> response = apiService.getCurrentWeather().execute();
            if (response.isSuccessful() && response.body() != null) {
                WeatherResponse weather = response.body();
                WeatherEntity entity = new WeatherEntity(
                        weather.temperature,
                        weather.humidity,
                        weather.condition,
                        System.currentTimeMillis()
                );
                weatherDao.insert(entity);
                errorMessage.postValue(null);
                Log.d("WeatherRepo", "Weather data saved: " + entity.temperature);
            } else {
                String error = "Server error: " + response.code();
                errorMessage.postValue(error);
                Log.e("WeatherRepo", error);
            }
        } catch (Exception e) {
            String error = "Network error: " + e.getMessage();
            errorMessage.postValue(error);
            Log.e("WeatherRepo", error, e);
        }
    }
        //for dummy testing
//    public void generateDummyDataForPastWeek() {
//        executor.execute(() -> {
//                    // Delete existing data
//                    weatherDao.deleteAll();
//                });
//    }
//
//            Calendar calendar = Calendar.getInstance();
//            long now = System.currentTimeMillis();
//
//            // Create 4 entries per day for 7 days
//            for (int day = 6; day >= 0; day--) {
//                for (int hour = 0; hour < 24; hour += 6) { // Every 6 hours
//                    long timestamp = now - TimeUnit.DAYS.toMillis(day) - TimeUnit.HOURS.toMillis(24 - hour);
//
//                    // Generate realistic weather values
//                    double temperature = generateTemperatureForTime(hour);
//                    int humidity = generateHumidityForTime(hour);
//                    String condition = generateConditionForTime(hour);
//
//                    WeatherEntity entity = new WeatherEntity(
//                            temperature,
//                            humidity,
//                            condition,
//                            timestamp
//                    );
//                    weatherDao.insert(entity);
//                }
//            }
//        });
//
//    }
//    private double generateTemperatureForTime(int hour) {
//        double baseTemp = 10.0 + Math.random() * 30.0;
//        double variation = Math.sin(Math.toRadians(hour * 15)) * 8.0;
//        return Math.round((baseTemp + variation) * 10.0) / 10.0;
//    }
//
//    private int generateHumidityForTime(int hour) {
//        int baseHumidity = 60;
//        int variation = (int) (Math.cos(Math.toRadians(hour * 15)) * 20);
//        return Math.max(30, Math.min(90, baseHumidity + variation));
//    }
//
//    private String generateConditionForTime(int hour) {
//        if (hour >= 6 && hour <= 18) {
//            return Math.random() > 0.7 ? "Partly Cloudy" : "Sunny";
//        } else {
//            return Math.random() > 0.5 ? "Cloudy" : "Rainy";
//        }
//    }

}