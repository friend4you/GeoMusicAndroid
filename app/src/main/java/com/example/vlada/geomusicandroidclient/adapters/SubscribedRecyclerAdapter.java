package com.example.vlada.geomusicandroidclient.adapters;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class SubscribedRecyclerAdapter extends Adapter<ViewHolder> {

    private List<Playlist> itemList;

    public SubscribedRecyclerAdapter() {
        this.itemList = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    public void add(List<Playlist> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_griditem, parent, false);
        return new PlaylistViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((PlaylistViewHolder) holder).bind(itemList.get(position));
    }


    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    class PlaylistViewHolder extends ViewHolder {

        private final TextView playlistTitle;
        private final TextView playlistAuthor;
        private final ImageView playlistImage;

        PlaylistViewHolder(View itemView) {
            super(itemView);
            playlistTitle = (TextView) itemView.findViewById(R.id.playlist_gridItemTitle);
            playlistAuthor = (TextView) itemView.findViewById(R.id.playlist_gridItemAuthor);
            playlistImage = (ImageView) itemView.findViewById(R.id.playlist_gridItemImage);

            itemView.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            });
        }

        void bind(Playlist playlist ) {
            playlistAuthor.setText(playlist.getAuthor());
            playlistTitle.setText(playlist.getTitle());
            playlistImage.setImageResource(playlist.getId());
            /*String url;
            url = playlist.getImage();

            Glide.with(playlistImage.getContext())
                    .load(url)
                    .placeholder(R.drawable.record_background)
                    .centerCrop()
                    .into(playlistImage);*/
        }
    }
}
