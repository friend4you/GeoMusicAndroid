package com.example.vlada.geomusicandroidclient;

import android.content.Context;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class Application extends android.app.Application{

    private static Application application;
    private Storage storage;




    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        storage = new Storage(this);
        Log.d("onCreate Application", "begin");
        Log.d("onCreate Application", "end");

    }

    public Storage getStorage() {
        return storage;
    }

    public static Application getSharedInstance() {
        return application;
    }

}
