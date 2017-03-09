package com.example.vlada.geomusicandroidclient;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.events.PlayRecordEvent;
import com.example.vlada.geomusicandroidclient.events.ShowRecordTitleEvent;
import com.example.vlada.geomusicandroidclient.fragments.SearchFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.annotation.ElementType.METHOD;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {


//    TabLayout tabs;

    private NavigationView navigationView;
    private TextView recordArtistSelected;
    private TextView recordTitleSelected;
    private ImageView recordImage;
    private ImageView playPause;
    private LinearLayout recordStrip;
    private LinearLayout recordStripInfo;
    public static Record currentRecord;
    private FrameLayout container;
    private int lastRecordId;

    final static String BROADCAST_ACTION = "BROADCAST_ACTION";
    public static final String BROADCAST_SEEK_CHANGED = "SEEK_CHANGED";
    static boolean keepplaying = false;
    static int playing = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();


        recordArtistSelected = (TextView) findViewById(R.id.record_author_selected);
        recordTitleSelected = (TextView) findViewById(R.id.record_title_selected);
        recordImage = (ImageView) findViewById(R.id.imageView4);
        recordImage.setImageResource(R.drawable.record_background);
        playPause = (ImageView) findViewById(R.id.main_playPause);
        playPause.setImageResource(R.drawable.ic_play_circle_outline_white_48dp);
        playPause.setOnClickListener(this::playPause);
        recordStripInfo = (LinearLayout) findViewById(R.id.main_record_strip_info);
        recordStripInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MediaPlayer.class);
            startActivity(intent);
        });
        recordStrip = (LinearLayout) findViewById(R.id.main_record_strip);


//        View page = inflater.inflate(R.layout.playlists_featured, null);
//        pages.add(page);
//
//        page = inflater.inflate(R.layout.playlists_trending, null);
//        pages.add(page);
//
//        page = inflater.inflate(R.layout.playlists_categories, null);
//        pages.add(page);
//
//        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);


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

        if (!Application.getSharedInstance().getStorage().isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        container = (FrameLayout) findViewById(R.id.container);
    }

    public void setupPlayerStrip(Record record) {
        if(record == null) {
            recordStrip.setVisibility(GONE);
            return;
        }

        recordStrip.setVisibility(VISIBLE);
        recordArtistSelected.setText(record.getArtist());
        recordTitleSelected.setText(record.getTitle());
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
            if (!Application.getSharedInstance().getStorage().isLoggedIn()) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
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
        Application.getSharedInstance().getStorage().logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void openSearch() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        List<View> pages = new ArrayList<View>();
//
//
//        View page = inflater.inflate(R.layout.record_search, null);
//        pages.add(page);
//
//        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);
        navigationView.setCheckedItem(R.id.nav_search);
        replaceFragment(new SearchFragment());

    }

    private void openNearYou() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        List<View> pages = new ArrayList<View>();
//
//        View page = inflater.inflate(R.layout.nearyou_map, null);
//        pages.add(page);
//
//        page = inflater.inflate(R.layout.nearyou_categories, null);
//        pages.add(page);
//
//        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_nearyou);
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    private void openSubscribed() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        List<View> pages = new ArrayList<View>();
//
//        View page = inflater.inflate(R.layout.subscribed_playlists, null);
//        pages.add(page);
//
//        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_subscribed);
    }

    private void openFavourites() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        List<View> pages = new ArrayList<View>();
//
//        View page = inflater.inflate(R.layout.favorites_records, null);
//        pages.add(page);
//
//        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_favorites);
    }

    private void openPlayList() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        List<View> pages = new ArrayList<View>();
//
//        View page = inflater.inflate(R.layout.playlists_featured, null);
//        pages.add(page);
//
//        page = inflater.inflate(R.layout.playlists_trending, null);
//        pages.add(page);
//
//        page = inflater.inflate(R.layout.playlists_categories, null);
//        pages.add(page);
//
//        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(0);

        navigationView.setCheckedItem(R.id.nav_playlists);
    }



    public void playPause(View view) {
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
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShowRecordTitleEvent event) {
        Record record = event.getRecord();
        if (record == null) return;
        if (recordStrip.getVisibility() == LinearLayout.GONE) {
            recordStrip.setVisibility(LinearLayout.VISIBLE);
        }

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
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayRecordEvent event) {
        Record record = event.getRecord();
        keepplaying = true;
        currentRecord = record;
        MusicService.activeRecord = record;
        playPause(playPause);
        Application.getSharedInstance().getStorage().saveLastSong(currentRecord);
    }
}
