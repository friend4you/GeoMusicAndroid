package com.example.vlada.geomusicandroidclient.api;

import com.example.vlada.geomusicandroidclient.api.model.RegistrationResponse;
import com.example.vlada.geomusicandroidclient.api.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GeomusicService {
    private final Api api;

    public GeomusicService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://geomusic.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public Observable<User> login(String email, String password) {
        Call<User> repos = api.getUserJson(email, password);
        return Observable.fromCallable(() -> repos.execute().body());
    }

    public Observable<RegistrationResponse> registration(String userName, String email, String password, String repeatPassword) {
        Call<RegistrationResponse> call = api.registerUser(userName, email, password, repeatPassword);
        return Observable.fromCallable(() -> call.execute().body());
    }
}
