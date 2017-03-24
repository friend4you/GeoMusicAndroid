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

import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.SubscribedRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.example.vlada.geomusicandroidclient.models.Playlist;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubscribedFragment extends Fragment {

    private List<Playlist> playlists;
    private GridLayoutManager lLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscribed_playlists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ServiceGenerator.getGeomusicService().getPlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            playlists = response;
                            lLayout = new GridLayoutManager(getActivity(), 3);
                            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.subscribed_recyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(lLayout);
                            SubscribedRecyclerAdapter subrAdapter = new SubscribedRecyclerAdapter(getActivity(), response);
                            recyclerView.setAdapter(subrAdapter);
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
