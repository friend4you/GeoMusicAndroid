package com.example.vlada.geomusicandroidclient.api;

import com.example.vlada.geomusicandroidclient.api.model.LoginResponse;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;
import com.example.vlada.geomusicandroidclient.api.model.RegistrationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @Headers("Content-Type: application/json")
    @GET("Account/JsonLogin")
    Call<LoginResponse> getUserJson(@Query("email") String email, @Query("password") String password);

    @FormUrlEncoded
    @Headers("client: mobile")
    @POST("Account/Register")
    Call<RegistrationResponse> registerUser(
            @Field("UserName") String userName,
            @Field("Email") String email,
            @Field("Password") String password,
            @Field("ConfirmPassword") String repeatPassword
    );

    @Headers("Content-Type: application/json")
    @GET("api/getplaylists")
    Call<List<Playlist>> getPlaylists();
}
