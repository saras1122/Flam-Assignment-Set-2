package com.example.flamassignment.workmanager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.startup.Initializer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkManagerInitializer implements Initializer<Void> {
    @NonNull
    @Override
    public Void create(@NonNull Context context) {
        PeriodicWorkRequest weatherWork = new PeriodicWorkRequest.Builder(
                WeatherSyncWorker.class,
                6,
                TimeUnit.HOURS
        ).build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "WeatherSync",
                ExistingPeriodicWorkPolicy.KEEP,
                weatherWork
        );
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}