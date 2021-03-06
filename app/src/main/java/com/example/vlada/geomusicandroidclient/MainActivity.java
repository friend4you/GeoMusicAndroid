package com.example.vlada.geomusicandroidclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.events.PlayRecordEvent;
import com.example.vlada.geomusicandroidclient.events.ShowRecordTitleEvent;
import com.example.vlada.geomusicandroidclient.fragments.FavouritesFragment;
import com.example.vlada.geomusicandroidclient.fragments.NearyouFragment;
import com.example.vlada.geomusicandroidclient.fragments.PlaylistsFragment;
import com.example.vlada.geomusicandroidclient.fragments.SubscribedFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity
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
    private ImageView userImage;
    private TextView userName;
    private FloatingActionButton fab;
    private Button manageButton;

    //final ActionBar actionBar = getActionBar();
    final static String BROADCAST_ACTION = "BROADCAST_ACTION";
    public static final String BROADCAST_SEEK_CHANGED = "SEEK_CHANGED";
    static boolean keepplaying = false;
    static int playing = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recordArtistSelected = (TextView) findViewById(R.id.record_author_selected);
        recordTitleSelected = (TextView) findViewById(R.id.record_title_selected);
        recordImage = (ImageView) findViewById(R.id.imageView4);
        recordImage.setImageResource(R.drawable.retro);
        playPause = (ImageView) findViewById(R.id.main_playPause);
        playPause.setImageResource(R.drawable.ic_play_circle_outline_white_48dp);
        playPause.setOnClickListener(this::playPause);
        recordStripInfo = (LinearLayout) findViewById(R.id.main_record_strip_info);
        recordStripInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MediaPlayer.class);
            startActivity(intent);
        });
        recordStrip = (LinearLayout) findViewById(R.id.main_record_strip);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Playlists");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_playlists);
        navigationView.setBackgroundColor(Color.WHITE);
        navigationView.setNavigationItemSelectedListener(this);


        container = (FrameLayout) findViewById(R.id.container);
        openPlayList();

        View headerView = navigationView.getHeaderView(0);
        userImage = (ImageView) headerView.findViewById(R.id.navbar_userImage);
        Glide.with(this)
                .load(Application.getSharedInstance().getStorage().getUserImage())
                .asBitmap()
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .centerCrop()
                .into(new BitmapImageViewTarget(userImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        userImage.setImageDrawable(roundedBitmapDrawable);
                    }
                });


        userImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AccountManager.class);
            startActivity(intent);
        });
        userName = (TextView) headerView.findViewById(R.id.navbar_userName);
        userName.setText(Application.getSharedInstance().getStorage().getUserNickname());

        manageButton = (Button) headerView.findViewById(R.id.navbar_manageButton);
        manageButton.setOnClickListener(c -> {
            Intent intent = new Intent(MainActivity.this, PlaylistManager.class);
            startActivity(intent);
        });

        openPlayList();

    }


    public void setupPlayerStrip(Record record) {
        if (record == null) {
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
        inflater.inflate(R.menu.nearyou_menu, menu);
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
            case R.id.nav_nearyou:
                openNearYou();
                break;
            case R.id.nav_settings:
                openSettings();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        Application.getSharedInstance().getStorage().logout();
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
    }

   /* private void openSearch() {
        navigationView.setCheckedItem(R.id.nav_search);
        getSupportActionBar().setTitle("Search");
        replaceFragment(new SearchFragment());

    }*/

    private void openNearYou() {
        navigationView.setCheckedItem(R.id.nav_nearyou);
        getSupportActionBar().setTitle("Near you");
        replaceFragment(new NearyouFragment());
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    private void openSubscribed() {
        navigationView.setCheckedItem(R.id.nav_subscribed);
        getSupportActionBar().setTitle("Subscibed");
        fab.setVisibility(GONE);
        replaceFragment(new SubscribedFragment());
    }

    private void openFavourites() {
        navigationView.setCheckedItem(R.id.nav_favorites);
        getSupportActionBar().setTitle("Favorites");
        fab.setVisibility(GONE);
        replaceFragment(new FavouritesFragment());
    }

    private void openPlayList() {
        navigationView.setCheckedItem(R.id.nav_playlists);
        getSupportActionBar().setTitle("Playlists");
        fab.setVisibility(GONE);

        replaceFragment(new PlaylistsFragment());
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
