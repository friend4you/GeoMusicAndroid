package com.example.vlada.geomusicandroidclient.fragments;



import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.TrendingRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;


import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TrendingController {

    private final RecyclerView recycler;
    private TrendingRecyclerAdapter adapter;
    private List<Playlist> playlists;

    public TrendingController(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.trending_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if (adapter == null) {
            adapter = new TrendingRecyclerAdapter();
        }
        recycler.setAdapter(adapter);





        playlists = com.example.vlada.geomusicandroidclient.Application.getSharedInstance().getStorage().getPlaylists();
        if(playlists == null){
            playlists = new ArrayList<>();
        }

        adapter.add(playlists);

    }

    private void fetchData() {
        ServiceGenerator.getGeomusicService().getPlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        playlists -> {
                            adapter.add(playlists);
                        }, error -> {
                            Log.d("failure", "failure");
                            Toast.makeText(recycler.getContext(), "Failed to get Subscribed", Toast.LENGTH_SHORT).show();
                        });


    }

    private <T> void initRecycle(T data) {
        //Set data to adapter
    }
}
