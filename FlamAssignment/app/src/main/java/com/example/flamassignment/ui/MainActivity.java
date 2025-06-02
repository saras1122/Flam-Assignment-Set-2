package com.example.flamassignment.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.flamassignment.R;
import com.example.flamassignment.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity {
    private WeatherViewModel viewModel;
    private TextView tempText, humidityText, conditionText;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tempText = findViewById(R.id.tempText);
        humidityText = findViewById(R.id.humidityText);
        conditionText = findViewById(R.id.conditionText);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.getLatestWeather().observe(this, weather -> {
            if (weather != null) {
                tempText.setText(String.format("%.1f°C", weather.temperature));
                humidityText.setText(String.format("%d%%", weather.humidity));
                conditionText.setText(weather.condition);
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.refreshWeather();
        });

        findViewById(R.id.viewWeeklyBtn).setOnClickListener(v -> {
            startActivity(WeeklySummaryActivity.newIntent(MainActivity.this));
        });
        findViewById(R.id.refreshButton).setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.forceRefresh();
        });
//        Button dummyDataBtn = findViewById(R.id.dummyDataBtn);
//        dummyDataBtn.setOnClickListener(v -> {
//            viewModel.generateDummyData();
//            Toast.makeText(this, "Generating 7 days of dummy data...", Toast.LENGTH_SHORT).show();
//        });



        progressBar.setVisibility(View.VISIBLE);
        viewModel.refreshWeather();
    }
}