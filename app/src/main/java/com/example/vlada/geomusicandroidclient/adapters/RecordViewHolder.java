package com.example.vlada.geomusicandroidclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.SearchRecordAdapter;
import com.example.vlada.geomusicandroidclient.api.model.Record;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView artist;

    public RecordViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.record_title);
        artist = (TextView) itemView.findViewById(R.id.record_artist);
    }

    public void bind(Record record, SearchRecordAdapter.RecordSelectedListener listener) {
        title.setText(record.getTitle());
        artist.setText(record.getArtist());
        itemView.setOnClickListener(v -> listener.recordSelected(record));
    }
}
