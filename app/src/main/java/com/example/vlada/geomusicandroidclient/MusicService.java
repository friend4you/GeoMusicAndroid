package com.example.vlada.geomusicandroidclient;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.vlada.geomusicandroidclient.api.model.Record;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class MusicService extends Service {

    final static String PLAY = "com.example.vladarsenyuk.mediaplayer.action.PLAY";
    final static String PAUSE = "com.example.vladarsenyuk.mediaplayer.action.PAUSE";


    public static Record activeRecord;
    public static Record pausedRecord;
    private int pausedTime = 0;

    public static boolean isInBackground;

    public static boolean isShuffle = false;
    public static boolean isRepeat = false;
    public static int queue = 1; //1 - forward, 2 - repeat, 3 - shuffle

    public static MediaPlayer mediaPlayer;
    private Intent seekIntent;
    private BroadcastReceiver seekChangingBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateSeekPos(intent);
        }
    };

    //Seekbar variables
    int mediaPosition;
    int mediaMax;
    private final Handler handler = new Handler();
    public static final String BROADCAST_SEEK = "BROADCAST_SEEK";

    //Call segment
    private boolean isPausedInCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;


    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(mp -> {
            Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
            sendBroadcast(intent);
        });

        mediaPlayer.setOnSeekCompleteListener(mp -> {
            //if(!mediaPlayer.isPlaying())
            //    play();
        });
        seekIntent = new Intent(BROADCAST_SEEK);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pause();
                            isPausedInCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (mediaPlayer != null) {
                            if (isPausedInCall) {
                                isPausedInCall = false;
                                play();
                            }
                        }
                }
            }
        };

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        Log.d("service", "created");


    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals(PLAY)) {
            play();
        } else if (action.equals(PAUSE)) {
            pause();
        }

        setupHandler();

        registerReceiver(seekChangingBroadcastReceiver, new IntentFilter(
                MainActivity.BROADCAST_SEEK_CHANGED));

        return START_NOT_STICKY;
    }

    public void play() {
        if (!activeRecord.equals(pausedRecord)) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(activeRecord.getPath());
                mediaPlayer.prepare();
            } catch (IOException e) {
                Log.e(TAG, "play: " + activeRecord.getPath(), e);
            }
            mediaPlayer.start();
        } else {
            mediaPlayer.seekTo(pausedTime);
            mediaPlayer.start();
            pausedTime = 0;
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pausedRecord = activeRecord;
            pausedTime = mediaPlayer.getCurrentPosition();
        }
    }

    public void setupHandler() {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000);
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        @Override
        public void run() {
            LogMediaPosition();
            handler.postDelayed(this, 1000);
        }
    };

    public void LogMediaPosition() {
        if (mediaPlayer.isPlaying()) {
            mediaPosition = mediaPlayer.getCurrentPosition();
            mediaMax = mediaPlayer.getDuration();

            Log.d(TAG, "LogMediaPosition: playing, mediaPosition: " + mediaPosition + "; mediaMax: " + mediaMax);
            seekIntent.putExtra("current", String.valueOf(mediaPosition));
            seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
            sendBroadcast(seekIntent);
        }
    }

    public void updateSeekPos(Intent intent) {
        int seekPos = intent.getIntExtra("seekpos", 0);
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacks(sendUpdatesToUI);
            mediaPlayer.seekTo(seekPos);
            mediaPlayer.start();
            setupHandler();
        } else {
            pausedTime = seekPos;
        }
    }

    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }

        if (phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }

        handler.removeCallbacks(sendUpdatesToUI);
        unregisterReceiver(seekChangingBroadcastReceiver);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
