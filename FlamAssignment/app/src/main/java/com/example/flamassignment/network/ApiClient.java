package com.example.flamassignment.network;

import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class ApiClient {
    private static final String BASE_URL = "https://api.weatherapp.com/";
    private static Retrofit retrofit = null;

    private static class MockInterceptor implements Interceptor {
        private final Random random = new Random();

        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            SystemClock.sleep(1500);

            double temperature = 15 + random.nextDouble() * 25;
            int humidity = 30 + random.nextInt(50);
            String condition = getRandomCondition();

            String json = String.format(Locale.US,
                    "{\"temperature\": %.1f, \"humidity\": %d, \"condition\": \"%s\"}",
                    temperature, humidity, condition);
            Log.d("final json",json);
            return new Response.Builder()
                    .code(200)
                    .message("OK")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(json, MediaType.parse("application/json")))
                    .build();
        }

        private String getRandomCondition() {
            String[] conditions = {"Sunny", "Cloudy", "Rainy", "Partly Cloudy", "Windy"};
            int i = random.nextInt(conditions.length);
            return conditions[i];
        }
    }

    public static ApiService getApiService() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new MockInterceptor())
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}