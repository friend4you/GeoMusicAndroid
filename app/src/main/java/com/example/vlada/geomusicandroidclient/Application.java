package com.example.vlada.geomusicandroidclient;

import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class Application extends android.app.Application{

    private static Application application;
    private Storage storage;


    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Log.d("onTokenChanged", "newToken == null");
            }
        }
    };



    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        storage = new Storage(this);
        vkAccessTokenTracker.startTracking();
        Log.d("onCreate Application", "begin");
        VKSdk.initialize(this);
        Log.d("onCreate Application", "end");

    }

    public Storage getStorage() {
        return storage;
    }

    public static Application getSharedInstance() {
        return application;
    }

}
