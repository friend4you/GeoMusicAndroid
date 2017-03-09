package com.example.vlada.geomusicandroidclient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vlada.geomusicandroidclient.api.model.Record;

import java.util.concurrent.TimeUnit;

import static com.example.vlada.geomusicandroidclient.MainActivity.BROADCAST_SEEK_CHANGED;
import static com.example.vlada.geomusicandroidclient.MainActivity.currentRecord;
import static com.example.vlada.geomusicandroidclient.MainActivity.keepplaying;
import static com.example.vlada.geomusicandroidclient.MainActivity.playing;

public class MediaPlayer extends Activity {


    private TextView artistTitle;
    private TextView duration;
    private TextView currentTime;
    private ImageView playPause;
    private SeekBar timeLine;
    private ImageView recordImage;

    private int seekMax;
    private NotificationManager notificationManager;


    BroadcastReceiver seekbr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateSeekBar(intent);
            if (MusicService.isInBackground)
                sendNotification();
        }
    };

    private void updateSeekBar(Intent serviceIntent) {
        int seekProgress = Integer.parseInt(serviceIntent.getStringExtra("current"));
        seekMax = Integer.parseInt(serviceIntent.getStringExtra("mediamax"));
        timeLine.setMax(seekMax);
        timeLine.setProgress(seekProgress);

        currentTime.setText(String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(seekProgress),
                TimeUnit.MILLISECONDS.toMinutes(seekProgress) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(seekProgress)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(seekProgress) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(seekProgress))));
        duration.setText(" / " + String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(seekMax),
                TimeUnit.MILLISECONDS.toMinutes(seekMax) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(seekMax)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(seekMax) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(seekMax))));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_play);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        artistTitle = (TextView) findViewById(R.id.record_play_artistTitle);
        duration = (TextView) findViewById(R.id.textViewRecordLength);
        currentTime = (TextView) findViewById(R.id.textViewCurrentTime);
        timeLine = (SeekBar) findViewById(R.id.record_play_timeLine);
        playPause = (ImageView) findViewById(R.id.record_play_playPause);
        recordImage = (ImageView) findViewById(R.id.record_play_recordImage);

        playPause.setOnClickListener(v -> PlayPause(v));

        if (MusicService.mediaPlayer != null) {
            if (MusicService.mediaPlayer.isPlaying()) {
                playing = 1;
                timeLine.setProgress(MusicService.mediaPlayer.getCurrentPosition());
                timeLine.setMax(MusicService.mediaPlayer.getDuration());
            }
        }


        if (playing == 1) {
            playPause.setImageResource(R.drawable.ic_pause_circle_outline_white_48dp);
        } else {
            playPause.setImageResource(R.drawable.ic_play_circle_outline_white_48dp);
        }
        String artistTitleText = currentRecord.getArtist() + " - " + currentRecord.getTitle();
        if (artistTitleText.length() > 35) {
            artistTitle.setText(artistTitleText.substring(0, 35) + "...");
        } else {
            artistTitle.setText(artistTitleText);
        }

        timeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int seekPos = seekBar.getProgress();
                    Intent seekChangedIntent = new Intent(BROADCAST_SEEK_CHANGED);
                    seekChangedIntent.putExtra("seekpos", seekPos);
                    sendBroadcast(seekChangedIntent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        registerReceiver(seekbr, new IntentFilter(MusicService.BROADCAST_SEEK));


    }


    public void PlayPause(View view) {
        Intent serviceIntent = new Intent(this, MusicService.class);
        if (keepplaying && playing == 1)
            playing = 0;

        Log.d("playing status", Integer.toString(playing));
        Log.d("keepplaying status", Boolean.toString(keepplaying));
        switch (playing) {
            case 0:
                playing = 1;
                playPause.setImageResource(R.drawable.ic_pause_circle_outline_white_48dp);
                keepplaying = false;
                serviceIntent.setAction(MusicService.PLAY);
                startService(serviceIntent);

                break;
            case 1:
                playing = 0;
                playPause.setImageResource(R.drawable.ic_play_circle_outline_white_48dp);
                keepplaying = false;
                serviceIntent.setAction(MusicService.PAUSE);
                startService(serviceIntent);

                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MusicService.isInBackground = true;
        sendNotification();
    }

    @Override
    public void onResume() {
        super.onResume();
        MusicService.isInBackground = false;
    }

    public String getSongTitle(Record record) {
        String song = record.getArtist() + " - " + record.getTitle();
        if (song.length() > 35) {
            song = song.substring(0, 35) + "...";
        }
        return song;
    }

    public void sendNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentIntent(pIntent)
                .setSmallIcon(R.drawable.ic_play_circle_outline_white_48dp)
                .setContentTitle(getSongTitle(MusicService.activeRecord))
                .setTicker("Your music is playing")
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setProgress(MusicService.mediaPlayer.getDuration(),
                        MusicService.mediaPlayer.getCurrentPosition(), false);

        Notification n = notification.build();
        notificationManager.notify(555, n);
    }

}
