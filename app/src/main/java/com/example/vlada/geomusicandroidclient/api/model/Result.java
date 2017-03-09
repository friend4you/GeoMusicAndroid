package com.example.vlada.geomusicandroidclient.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("Succeeded")
    private Boolean succeeded;
    @SerializedName("Errors")
    private List<String> errors = null;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public List<String> getErrors() {
        return errors;
    }
}