package com.example.vlada.geomusicandroidclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlada on 09.04.2017.
 */

public class TrendingRecyclerAdapter extends Adapter<ViewHolder> {

    private List<Playlist> items;

    public TrendingRecyclerAdapter() {
        items = new ArrayList<Playlist>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public TrendingRecyclerAdapter.PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_line, parent, false);
        return new TrendingRecyclerAdapter.PlaylistViewHolder(layoutView);

    }

    public void add(List<Playlist> itemList) {
        this.items.clear();
        this.items.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((PlaylistViewHolder) holder).bind(items.get(position));
    }


    class PlaylistViewHolder extends ViewHolder {

        private TextView title;
        private TextView description;
        private ImageView image;
        private ImageView addButton;


        PlaylistViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
            description = (TextView) itemView.findViewById(R.id.description);
            addButton = (ImageView) itemView.findViewById(R.id.addButton);
            addButton.setOnClickListener(v -> {

            });
        }

        void bind(Playlist playlist) {
            title.setText(playlist.getTitle());
            description.setText(playlist.getDescription());
            image.setImageResource(playlist.getId());
            /*String url;
            url = playlist.getImage();*/
            Glide.with(image.getContext())
                    .load(playlist.getId())
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);
            addButton.setImageResource(R.drawable.ic_add_circle_outline_black_48dp);

            itemView.setOnClickListener(v -> {

            });
        }
    }
}
