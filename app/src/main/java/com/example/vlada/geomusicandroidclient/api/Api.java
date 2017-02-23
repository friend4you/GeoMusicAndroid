package com.example.vlada.geomusicandroidclient.api;


import com.example.vlada.geomusicandroidclient.api.model.LoginResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vlada on 02.02.2017.
 */

public interface Api {

    @Headers("Content-Type: application/json")
    @GET("Account/JsonLogin")
    Call<LoginResponse> getUserJson(@Query("email") String email, @Query("password") String password);


}
