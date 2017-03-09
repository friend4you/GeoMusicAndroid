package com.example.vlada.geomusicandroidclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vlada.geomusicandroidclient.api.model.Record;

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
}
