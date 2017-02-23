package com.example.vlada.geomusicandroidclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.api.model.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlada on 19.02.2017.
 */

public class SearchRecordAdapter extends ArrayAdapter<Record> {
    private List<Record> records;
    private LayoutInflater songsInflater;

    public SearchRecordAdapter(Context context, List<Record> records) {
        super(context, R.layout.record_search_item, records);
        this.records = records;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(songsInflater == null) {
            songsInflater = LayoutInflater.from(getContext());
        }
        View myView = songsInflater.inflate(R.layout.record_search_item, parent, false);

        Record item = getItem(position);
        TextView title = (TextView) myView.findViewById(R.id.record_title);
        TextView artist = (TextView) myView.findViewById(R.id.record_artist);

        title.setText(item.getTitle());
        artist.setText(item.getArtist());

        return myView;

    }


    @Override
    public void add(Record object) {
        records.add(object);
    }

    @Nullable
    @Override
    public Record getItem(int position) {
        return records.get(position);
    }

    @Override
    public int getCount() {
        return records.size();
    }


}
