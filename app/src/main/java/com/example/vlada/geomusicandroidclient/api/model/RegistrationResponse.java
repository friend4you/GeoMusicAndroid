package com.example.vlada.geomusicandroidclient.api.model;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("result")
    private Result result;
    @SerializedName("user")
    private User user;

    public Result getResult() {
        return result;
    }
    public User getUser() {
        return user;
    }
}
