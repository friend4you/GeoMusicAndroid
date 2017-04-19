package com.example.vlada.geomusicandroidclient.api;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.api.model.LoginResponse;
import com.example.vlada.geomusicandroidclient.api.model.RegistrationResponse;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GeomusicService {
    private final Api api;

    public GeomusicService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(Application.getSharedInstance().getApplicationContext()))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://geomusic.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        api = retrofit.create(Api.class);
    }

    public Observable<LoginResponse> login(String email, String password) {
        Call<LoginResponse> repos = api.getUserJson(email, password);
        return Observable.fromCallable(() -> repos.execute().body());
    }

    public Observable<RegistrationResponse> registration(String userName, String email, String password, String repeatPassword) {
        Call<RegistrationResponse> call = api.registerUser(userName, email, password, repeatPassword);
        return Observable.fromCallable(() -> call.execute().body());
    }
    public Observable<List<Playlist>> getPlaylists(){
        Call<List<Playlist>> call = api.getPlaylists();
        return Observable.fromCallable(() -> call.execute().body());
    }
    public Observable<List<Category>> getCategories(){
        Call<List<Category>> call = api.getCategories();
        return Observable.fromCallable(() -> call.execute().body());
    }
}
