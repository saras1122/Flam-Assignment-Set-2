package com.example.flamassignment.workmanager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.flamassignment.repository.WeatherRepository;
import com.example.flamassignment.WeatherApp;

public class WeatherSyncWorker extends Worker {
    public WeatherSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        WeatherRepository repository = new WeatherRepository((WeatherApp) getApplicationContext());
        repository.fetchAndSaveWeather();
        return Result.success();
    }
}