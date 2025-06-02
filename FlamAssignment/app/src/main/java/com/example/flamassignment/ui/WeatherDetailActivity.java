package com.example.flamassignment.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flamassignment.R;
import com.example.flamassignment.persistence.model.WeatherEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        WeatherEntity weather = (WeatherEntity) getIntent().getSerializableExtra("WEATHER_DATA");

        TextView dateText = findViewById(R.id.dateText);
        TextView tempText = findViewById(R.id.detailTempText);
        TextView humidityText = findViewById(R.id.detailHumidityText);
        TextView conditionText = findViewById(R.id.detailConditionText);

        if (weather != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
            dateText.setText(sdf.format(new Date(weather.timestamp)));
            tempText.setText(String.format("%.1f°C", weather.temperature));
            humidityText.setText(String.format("Humidity: %d%%", weather.humidity));
            conditionText.setText(String.format("Condition: %s", weather.condition));
        }
    }
}