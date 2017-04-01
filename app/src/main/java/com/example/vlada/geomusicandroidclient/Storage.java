package com.example.vlada.geomusicandroidclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.api.model.User;

import static android.content.Context.MODE_PRIVATE;

public class Storage {
    private final SharedPreferences sharedPreferences;
    private final String PREFERENCE_NAME = "settings";
    private final String IS_AUTHORIZED_TAG = "is_authorized";
    private final String EMAIL_TAG = "email";
    private final String PASSWORD = "password";
    private final String RECORD_ID_TAG = "record_id";
    private final String RECORD_LOACTION_TAG = "record_location";
    private final String LOCAL_TAG = "local";
    private final String SERVER_TAG = "server";

    public Storage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_AUTHORIZED_TAG, false);
    }

    public void setLogin(boolean login) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putBoolean(IS_AUTHORIZED_TAG, login);
        ed.apply();
    }

    public void logout() {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putBoolean(IS_AUTHORIZED_TAG, false);
        ed.putString(EMAIL_TAG, "");
        ed.putString(PASSWORD, "");
        ed.apply();
    }

    public void saveLastSong(Record record) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(RECORD_ID_TAG, record.getId());
        if (record.getDate() == null) {
            ed.putString(RECORD_LOACTION_TAG, LOCAL_TAG);
        } else {
            ed.putString(RECORD_LOACTION_TAG, SERVER_TAG);
        }
        ed.apply();
    }

    public int getLastRecordId() {
        return sharedPreferences.getInt("record_id", -1);
    }

    public void saveUserInfo(User user) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("TOKEN", user.getToken());
        ed.putString("USER_ID", user.getId());
        ed.putString("USER_NICKNAME", user.getUserName());
        ed.putString("USER_IMAGE", user.getUserImage());
    }

    public String getUserToken() {
        return sharedPreferences.getString("TOKEN", "0");
    }

    public String getUserId() {
        return sharedPreferences.getString("USER_ID", "0");
    }

    public String getUserNickname() {
        return sharedPreferences.getString("USER_NICKNAME", "DefaultUserName");
    }

    public String getUserImage() {
        return sharedPreferences.getString("USER_IMAGE", "Default");
    }
}
