package com.example.vlada.geomusicandroidclient.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.HomeRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.model.CategoryPlaylists;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeController {

    private final RecyclerView recycler;
    private HomeRecyclerAdapter adapter;
    private List<CategoryPlaylists> items;
    private List<Playlist> playlists;
    private List<Playlist> headerPlaylists;
    private List<Playlist> recomendedPlaylists;
    private List<Playlist> category1;
    private List<Playlist> category2;
    private List<Playlist> category3;

    public HomeController(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.featured_recycler);
        if (adapter == null) {
            adapter = new HomeRecyclerAdapter();
        }
        recycler.setAdapter(adapter);

        playlists = Application.getSharedInstance().getStorage().getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
        }
        headerPlaylists = new ArrayList<>();
        recomendedPlaylists = new ArrayList<>();
        category1 = new ArrayList<>();
        category2 = new ArrayList<>();
        category3 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            category1.add(playlists.get(r.nextInt(8)));
            category2.add(playlists.get(r.nextInt(8)));
            category3.add(playlists.get(r.nextInt(8)));
            recomendedPlaylists.add(playlists.get(r.nextInt(8)));
        }
        for (int i = 0; i < 5; i++) {
            headerPlaylists.add(playlists.get(r.nextInt(8)));
        }

        items = new ArrayList<>();
        items.add(new CategoryPlaylists("Header", headerPlaylists));
        items.add(new CategoryPlaylists("Recomended", recomendedPlaylists));
        items.add(new CategoryPlaylists("Rock", category1));
        items.add(new CategoryPlaylists("Hip-Hop", category2));
        items.add(new CategoryPlaylists("Reggie", category3));


        adapter.add(items);
    }

    private void fetchData() {
        // TODO: 08.04.2017 get data from the server
    }

    private <T> void initRecycle(T data) {
        //Set data to adapter
    }


}
