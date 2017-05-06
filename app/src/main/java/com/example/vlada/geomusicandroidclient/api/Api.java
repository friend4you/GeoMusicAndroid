package com.example.vlada.geomusicandroidclient.api;

import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.api.model.LoginResponse;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;
import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.api.model.RegistrationResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("Account/JsonLogin")
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

    @Multipart
    @POST("Api/SaveUserImage")
    Call<Object> SaveUserImage(
            @Part("description")RequestBody description,
            @Part MultipartBody.Part file
            );

    @Headers("Content-Type: application/json")
    @GET("api/getcategories")
    Call<List<Category>> getCategories();

    @Headers("Content-Type: application/json")
    @POST("api/GetNearRecords")
    Call<List<Record>> getNearRecords(@Query("latitude") Double latitude, @Query("longitude") Double longitude);

    /*@Headers("Content-Type: application/json")
    @POST("api/")*/
}
