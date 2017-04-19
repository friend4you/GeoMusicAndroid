package com.example.vlada.geomusicandroidclient;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by vlada on 01.04.2017.
 */

public class ChangePassword extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("changepass create", "create");
        setContentView(R.layout.account_changepassword);


    }
}
