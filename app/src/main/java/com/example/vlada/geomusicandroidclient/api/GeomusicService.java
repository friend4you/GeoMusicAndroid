package com.example.vlada.geomusicandroidclient.api;

import android.content.pm.LauncherApps;
import android.util.Log;

import com.example.vlada.geomusicandroidclient.api.model.LoginResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by vlada on 05.02.2017.
 */

public class GeomusicService {
    private final Api api;

    public GeomusicService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://geomusic.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public Observable<LoginResponse> login(String email, String password) {
        Call<LoginResponse> repos = api.getUserJson(email, password);
        return Observable.fromCallable(() -> repos.execute().body());
    }
}
