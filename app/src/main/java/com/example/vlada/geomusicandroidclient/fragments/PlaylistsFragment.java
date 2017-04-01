package com.example.vlada.geomusicandroidclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vlada.geomusicandroidclient.MainPagerAdapter;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;


public class PlaylistsFragment extends Fragment {

    private List<Playlist> trendingPlaylist;
    private List<Playlist> top3Playlist;
    private List<Playlist> category1Playlist;
    private List<Playlist> category2Playlist;
    private List<Playlist> category3Playlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.playlists_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        List<View> pages = new ArrayList<>();

        View page = inflater.inflate(R.layout.playlists_featured, null);
        pages.add(page);

        page = inflater.inflate(R.layout.playlists_trending, null);
        pages.add(page);

        page = inflater.inflate(R.layout.categories, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.playlists_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View fillFeatured(View page, View view){

        /*ServiceGenerator.getGeomusicService().getPlaylists()
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
*/
        return page;
    }

    public View fillTrending(View page){

        RecyclerView trendingRecycler = (RecyclerView) getView().findViewById(R.id.trending_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        return page;
    }

    public View fillCategories(View page){

        return page;
    }
}
