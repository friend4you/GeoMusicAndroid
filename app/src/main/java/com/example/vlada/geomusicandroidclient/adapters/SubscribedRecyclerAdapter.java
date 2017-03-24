package com.example.vlada.geomusicandroidclient.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.models.Playlist;

import java.util.List;

public class SubscribedRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<Playlist> itemList;
    private Context context;

    public SubscribedRecyclerAdapter(Context context, List<Playlist> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {



        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_griditem, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.playlistAuthor.setText(itemList.get(position).getAuthor());
        holder.playlistTitle.setText(itemList.get(position).getTitle());
        String url;
        url = itemList.get(position).getImage();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.record_background)
                .centerCrop()
                .into(holder.playlistImage);
    }





    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}

class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView playlistTitle;
    public TextView playlistAuthor;
    public ImageView playlistImage;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        playlistTitle = (TextView) itemView.findViewById(R.id.playlist_gridItemTitle);
        playlistAuthor = (TextView) itemView.findViewById(R.id.playlist_gridItemAuthor);
        playlistImage = (ImageView) itemView.findViewById(R.id.playlist_gridItemImage);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
