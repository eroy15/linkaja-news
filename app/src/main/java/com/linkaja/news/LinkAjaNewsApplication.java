package com.linkaja.news;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linkaja.news.data.Constant;

import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LinkAjaNewsApplication extends Application {
    private Retrofit retrofit;
    private static LinkAjaNewsApplication linkAjaNewsApplication;
    int cacheSize = 10 * 1024 * 1024;
    final int TIMEOUT = 60;
    private Cache cache;

    @Override
    public void onCreate() {
        super.onCreate();
        linkAjaNewsApplication = this;
        cache = new Cache(getCacheDir(), cacheSize);

    }

    public static synchronized LinkAjaNewsApplication getInstance() {
        return linkAjaNewsApplication;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}