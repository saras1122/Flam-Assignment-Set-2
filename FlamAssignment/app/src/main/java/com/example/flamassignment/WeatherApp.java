package com.example.flamassignment;

import android.app.Application;
import android.util.Log;

import androidx.work.WorkManager;
import com.example.flamassignment.workmanager.WorkManagerInitializer;

public class WeatherApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WorkManagerInitializer initializer = new WorkManagerInitializer();
        Log.d("begins","process started");
        initializer.create(this);
    }
}