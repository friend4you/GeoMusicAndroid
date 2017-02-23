package com.example.vlada.geomusicandroidclient;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by vlada on 09.02.2017.
 */

class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);


    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Settings.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Settings.password = password;
    }

    public static Boolean getAuthorized() {
        return authorized;
    }

    public static void setAuthorized(Boolean authorized) {
        Settings.authorized = authorized;
    }

    private static String email;
    private static String password;

    private static Boolean authorized;
}
