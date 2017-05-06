package com.example.vlada.geomusicandroidclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.SubscribedRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubscribedFragment extends Fragment {

    private RecyclerView recycler;
    private List<Playlist> playlists;

    private SubscribedRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscribed_playlists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = (RecyclerView) view.findViewById(R.id.subscribed_recyclerView);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        if (adapter == null) {
            adapter = new SubscribedRecyclerAdapter();
        }

        recycler.setAdapter(adapter);

        playlists = Application.getSharedInstance().getStorage().getPlaylists();
        if (playlists == null) {
            playlists = new ArrayList<>();
        }

        adapter.add(playlists);
        //fetchPlaylist();
    }

    private void fetchPlaylist() {
        ServiceGenerator.getGeomusicService().getPlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        playlists -> {
                            adapter.add(playlists);
                        }, error -> {
                            Log.d("failure", "failure");
                            Toast.makeText(getActivity(), "Failed to get Subscribed", Toast.LENGTH_SHORT).show();
                        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
