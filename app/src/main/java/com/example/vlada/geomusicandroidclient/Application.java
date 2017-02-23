package com.example.vlada.geomusicandroidclient;

import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by vlada on 20.01.2017.
 */

public class Application extends android.app.Application{

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
        vkAccessTokenTracker.startTracking();
        Log.d("onCreate Application", "begin");
        VKSdk.initialize(this);
        Log.d("onCreate Application", "end");

    }
}
