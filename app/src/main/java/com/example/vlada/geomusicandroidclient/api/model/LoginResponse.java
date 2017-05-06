package com.example.vlada.geomusicandroidclient.api.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    private int result;
    @SerializedName("user")
    private User user;

    public int getResult() {
        return result;
    }

    public User getUser() {
        return user;
    }
}
