package com.example.vlada.geomusicandroidclient.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.SubscribedRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeController{

    private final RecyclerView recycler;
    private List<Playlist> playlists;
    private List<Playlist> hotPlaylists;
    private List<Playlist> recomendedPlaylists;
    private List<Playlist> category1;
    private List<Playlist> category2;
    private List<Playlist> category3;

    public HomeController(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.featured_recycler);
        playlists = Application.getSharedInstance().getStorage().getPlaylists();
        if(playlists == null){
            playlists = new ArrayList<>();
        }
        hotPlaylists = new ArrayList<>();
        recomendedPlaylists = new ArrayList<>();
        category1 = new ArrayList<>();
        category2 = new ArrayList<>();
        category3 = new ArrayList<>();
        Random r = new Random();
        for(int i=0; i<3; i++){
            category1.add(playlists.get(r.nextInt(8)));
            category2.add(playlists.get(r.nextInt(8)));
            category3.add(playlists.get(r.nextInt(8)));
            recomendedPlaylists.add(playlists.get(r.nextInt(8)));
        }
        for(int i=0; i<5; i++) {
            hotPlaylists.add(playlists.get(r.nextInt(8)));
        }



    }

    private void fetchData() {
        // TODO: 08.04.2017 get data from the server
    }

    private <T> void initRecycle(T data) {
       //Set data to adapter
    }


}
