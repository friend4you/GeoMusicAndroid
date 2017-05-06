package com.example.vlada.geomusicandroidclient;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.vlada.geomusicandroidclient.adapters.PlaylistManagerPagerAdapter;
import com.example.vlada.geomusicandroidclient.adapters.SubscribedRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView image;
    private RecyclerView recycler;
    private SubscribedRecyclerAdapter adapter;
    private List<Playlist> playlists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_manager);

        playlists = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        recycler  = (RecyclerView) findViewById(R.id.managerRecycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        if (adapter == null) {
            adapter = new SubscribedRecyclerAdapter();
        }
        recycler.setAdapter(adapter);
        playlists.add(Application.getSharedInstance().getStorage().getPlaylists().get(8));
        if (playlists == null) {
            playlists = new ArrayList<>();
        }

        adapter.add(playlists);

        image = (ImageView) findViewById(R.id.account_userImage);

        PlaylistManagerPagerAdapter pagerAdapter = new PlaylistManagerPagerAdapter();

        /*ViewPager viewPager = (ViewPager) findViewById(R.id.playlist_manager_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);*/

        Glide.with(this)
                .load(R.drawable.harry)
                .asBitmap()
                .placeholder(R.drawable.harry)
                .centerCrop()
                .into(new BitmapImageViewTarget(image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        image.setImageDrawable(roundedBitmapDrawable);
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playlist_manager_menu, menu);
        return true;
    }

}
