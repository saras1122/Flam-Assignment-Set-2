package com.example.flamassignment.network;

import com.example.flamassignment.network.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather();
}