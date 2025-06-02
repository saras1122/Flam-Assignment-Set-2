package com.example.flamassignment.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.flamassignment.R;
import com.example.flamassignment.persistence.model.WeatherEntity;
import com.example.flamassignment.viewmodel.WeatherViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.*;

public class WeeklySummaryActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private WeatherViewModel viewModel;
    private LineChart chart;
    private List<WeatherEntity> weeklyWeather = new ArrayList<>();
    private Map<Long, WeatherEntity> dailyLatestWeather = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_summary);
        chart = findViewById(R.id.chart);

        setupChart();
        setupViewModel();
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setOnChartValueSelectedListener(this);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(40f);
        leftAxis.setDrawGridLines(true);
        chart.getAxisRight().setEnabled(false);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getWeeklyWeather().observe(this, weatherList -> {
            if (weatherList != null && !weatherList.isEmpty()) {
                weeklyWeather = weatherList;
                processWeatherData();
                updateChart();
            }
        });
    }

    private void processWeatherData() {
        dailyLatestWeather.clear();
        for (WeatherEntity weather : weeklyWeather) {
            long dayStart = getStartOfDay(weather.timestamp);
            WeatherEntity existing = dailyLatestWeather.get(dayStart);
            if (existing == null || weather.timestamp > existing.timestamp) {
                dailyLatestWeather.put(dayStart, weather);
            }
        }
    }

    private long getStartOfDay(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.d("calender:", ""+calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    private void updateChart() {
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            long dayStart = getStartOfDay(calendar.getTimeInMillis());

            WeatherEntity weather = dailyLatestWeather.get(dayStart);
            if (weather != null) {
                entries.add(new Entry(6 - i, (float) weather.temperature));
            }
            labels.add(sdf.format(calendar.getTime()));
        }

        List<String> reversedLabels = new ArrayList<>();
        for (int i = labels.size() - 1; i >= 0; i--) {
            reversedLabels.add(labels.get(i));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Temperature (°C)");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(reversedLabels));

        chart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int index = (int) e.getX();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -(6 - index));
        long dayStart = getStartOfDay(calendar.getTimeInMillis());

        WeatherEntity weather = dailyLatestWeather.get(dayStart);
        if (weather != null) {
            Intent intent = new Intent(this, WeatherDetailActivity.class);
            intent.putExtra("WEATHER_DATA", weather);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected() {}

    public static Intent newIntent(Context context) {
        return new Intent(context, WeeklySummaryActivity.class);
    }
}
