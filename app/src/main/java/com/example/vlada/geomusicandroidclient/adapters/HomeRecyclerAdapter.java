package com.example.vlada.geomusicandroidclient.adapters;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.CategoryPlaylists;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeRecyclerAdapter extends Adapter<ViewHolder> {

    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_HEADER = 1;

    private List<CategoryPlaylists> playlistsCategories;

    public HomeRecyclerAdapter(){
        playlistsCategories = new ArrayList<>();
    }

    public void add(List<CategoryPlaylists> items){
        playlistsCategories.clear();
        playlistsCategories.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_HEADER) {
            View headerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_slider, parent, false);
            return new MyHeaderViewHolder(headerRow);
        }else{
            View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlists_category_griditem, parent, false);
            return new MyNormalViewHolder(normalView);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);

        if (itemType == ITEM_TYPE_NORMAL) {
            ((MyNormalViewHolder) holder).bind(playlistsCategories.get(position));
        } else if (itemType == ITEM_TYPE_HEADER) {
            ((MyHeaderViewHolder) holder).bind(playlistsCategories.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (playlistsCategories.get(position).getTitle().equals("Header")) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return playlistsCategories.size();
    }


    class MyHeaderViewHolder extends ViewHolder {

        private ViewPager pager;
        private PlaylistsSliderAdapter adapter;
        private CirclePageIndicator indicator;

        public MyHeaderViewHolder(View view) {
            super(view);

            pager = (ViewPager) view.findViewById(R.id.pager);
            indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
            if(adapter == null){
                adapter = new PlaylistsSliderAdapter();
            }
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
            final float density = view.getResources().getDisplayMetrics().density;
            indicator.setRadius(5 * density);
        }

        public void bind(CategoryPlaylists categoryPlaylists) {
            adapter.add(categoryPlaylists.getPlaylists());
        }
    }


    class MyNormalViewHolder extends ViewHolder {

        private TextView title;
        private RecyclerView recycler;
        private SubscribedRecyclerAdapter adapter;

        public MyNormalViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.category);
            recycler  = (RecyclerView) view.findViewById(R.id.categoryGridItems);
            recycler.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
            if (adapter == null) {
                adapter = new SubscribedRecyclerAdapter();
            }
            recycler.setAdapter(adapter);
        }

        public void bind(CategoryPlaylists categoryPlaylists) {
            adapter.add(categoryPlaylists.getPlaylists());
            title.setText(categoryPlaylists.getTitle());
        }
    }


}
