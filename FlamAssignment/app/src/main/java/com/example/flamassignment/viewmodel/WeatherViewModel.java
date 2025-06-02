package com.example.flamassignment.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.flamassignment.repository.WeatherRepository;
import com.example.flamassignment.persistence.model.WeatherEntity;
import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private final WeatherRepository repository;
    private final LiveData<WeatherEntity> latestWeather;
    private final LiveData<List<WeatherEntity>> weeklyWeather;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public WeatherViewModel(Application application) {
        super(application);
        repository = new WeatherRepository(application);
        latestWeather = repository.getLatestWeather();
        weeklyWeather = repository.getWeeklyWeather();

        repository.getErrorMessage().observeForever(error -> {
            errorMessage.postValue(error);
        });
    }

    public LiveData<WeatherEntity> getLatestWeather() {
        return latestWeather;
    }

    public LiveData<List<WeatherEntity>> getWeeklyWeather() {
        return weeklyWeather;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void refreshWeather() {
        repository.fetchAndSaveWeather();
//        repository.generateDummyDataForPastWeek();
    }

    public void forceRefresh() {
        repository.fetchAndSaveWeatherSync();
    }
//    public void generateDummyData() {
//        repository.generateDummyDataForPastWeek();
//    }


}