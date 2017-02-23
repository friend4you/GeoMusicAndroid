package com.example.vlada.geomusicandroidclient;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabs;
    SharedPreferences sPref;
    NavigationView navigationView;
    TextView recordArtistSelected;
    TextView recordTitleSelected;
    ImageView recordImage;
    ImageView playPause;
    LinearLayout recordStrip;
    static Record currentRecord;

    final static int PERMISSION_REQUEST_CODE = 555;
    final static String BROADCAST_ACTION = "BROADCAST_ACTION";
    public static final String BROADCAST_SEEK_CHANGED = "SEEK_CHANGED";
    private List<Record> recordList = new ArrayList<Record>();
    private RecyclerView musicRecycler;
    private SearchRecordAdapter searchRecordAdapter;
    static boolean keepplaying = false;
    static int playing = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Application().onCreate();


        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        recordArtistSelected = (TextView) findViewById(R.id.record_author_selected);
        recordTitleSelected = (TextView) findViewById(R.id.record_title_selected);
        recordImage = (ImageView) findViewById(R.id.imageView4);
        recordImage.setImageResource(R.drawable.record_background);
        playPause = (ImageView) findViewById(R.id.main_playPause);
        playPause.setImageResource(R.drawable.ic_play_circle_outline_white_48dp);
        playPause.setOnClickListener(v -> PlayPause(v));
        recordStrip = (LinearLayout) findViewById(R.id.main_record_strip);
        recordStrip.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MediaPlayer.class);
            startActivity(intent);
        });

        View page = inflater.inflate(R.layout.playlists_featured, null);
        pages.add(page);

        page = inflater.inflate(R.layout.playlists_trending, null);
        pages.add(page);

        page = inflater.inflate(R.layout.playlists_categories, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_playlists);
        navigationView.setBackgroundColor(Color.argb(255, 50, 200, 255));
        navigationView.setNavigationItemSelectedListener(this);
        sPref = getSharedPreferences("settings", MODE_PRIVATE);

        Log.d("onCreate MainActivity", "VkSdk initialized");


        if (!sPref.getBoolean("is_authorized", false)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
                    },
                    PERMISSION_REQUEST_CODE);
        } else {
            getRecords();
            if (sPref.getString("record_location", "null") == "local") {
                recordArtistSelected.setText(recordList.get(sPref.getInt("record_id", 0)).getArtist());
                recordTitleSelected.setText(recordList.get(sPref.getInt("record_id", 0)).getTitle());
                currentRecord = recordList.get(sPref.getInt("record_id", 0));
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (sPref.getBoolean("is_authorized", false)) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_playlists:
                openPlayList();
                break;
            case R.id.nav_favorites:
                openFavourites();
                break;
            case R.id.nav_subscribed:
                openSubscribed();
                break;
            case R.id.nav_settings:
                openSettings();
                break;
            case R.id.nav_nearyou:
                openNearYou();
                break;
            case R.id.nav_search:
                openSearch();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("is_authorized", false);
        ed.putString("email", "");
        ed.putString("password", "");
        ed.apply();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void openSearch() {
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.record_search, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        navigationView.setCheckedItem(R.id.nav_search);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecordAdapter = new SearchRecordAdapter(recordList, record -> {
            showSongTitle(record);
            keepplaying = true;
            currentRecord = record;
            MusicService.activeRecord = record;
            PlayPause(playPause);
        });
        musicRecycler = (RecyclerView) findViewById(R.id.music_recycler);
        musicRecycler.setLayoutManager(manager);
        musicRecycler.setAdapter(searchRecordAdapter);
    }

    private void openNearYou() {
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.nearyou_map, null);
        pages.add(page);

        page = inflater.inflate(R.layout.nearyou_categories, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_nearyou);
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    private void openSubscribed() {
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.subscribed_playlists, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_subscribed);
    }

    private void openFavourites() {
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.favorites_records, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_favorites);
    }

    private void openPlayList() {
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.playlists_featured, null);
        pages.add(page);

        page = inflater.inflate(R.layout.playlists_trending, null);
        pages.add(page);

        page = inflater.inflate(R.layout.playlists_categories, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_playlists);
    }

    public void getRecords() {
        recordList.clear();
        String[] columns = {MediaStore.Audio.AudioColumns._ID, MediaStore.Audio.AudioColumns.ARTIST, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.DATA};
        String where = MediaStore.Audio.AudioColumns.IS_MUSIC + " <> 0";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, where, null, null);

        while (cursor.moveToNext()) {
            Record n = new Record(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            recordList.add(n);
        }
        searchRecordAdapter.notifyDataSetChanged();

        if (searchRecordAdapter.getItemCount() != 0)
            showSongTitle(MusicService.activeRecord);
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

    public void showSongTitle(Record record) {
        String artist = record.getArtist();
        String title = record.getTitle();
        String artistTitle = record.getArtist() + " - " + record.getTitle();

        if (record.getArtist().length() > 35) {
            artist = record.getArtist().substring(0, 35) + "...";
        }
        if (record.getTitle().length() > 35) {
            title = record.getTitle().substring(0, 35) + "...";
        }
        if (artistTitle.length() > 35) {
            artistTitle = artistTitle.substring(0, 35) + "...";
        }
        recordArtistSelected.setText(artist);
        recordTitleSelected.setText(title);

        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("current_record", artistTitle);
        ed.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("record_id", currentRecord.getId());
        if (currentRecord.getDate() == null) {
            ed.putString("record_location", "local");
        } else {
            ed.putString("record_location", "server");
        }

    }
}
