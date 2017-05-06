package com.example.vlada.geomusicandroidclient;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.api.model.Record;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchRecordAdapter extends Adapter<ViewHolder> {
    private final List<Record> records;
    private final RecordSelectedListener listener;

    public SearchRecordAdapter(List<Record> records, RecordSelectedListener listener) {
        this.records = records;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater from = LayoutInflater.from(parent.getContext());
        return new RecordViewHolder(from.inflate(R.layout.item_record_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((RecordViewHolder) holder).bind(records.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public interface RecordSelectedListener {
        void recordSelected(Record record);
    }

    public class RecordViewHolder extends ViewHolder {

        private final TextView title;
        private final TextView artist;
        private final TextView duration;

        private final CardView cv;

        public RecordViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.record_title);
            artist = (TextView) itemView.findViewById(R.id.record_artist);
            duration = (TextView) itemView.findViewById(R.id.item_record_search_duration);



            cv = (CardView) itemView.findViewById(R.id.cv);
        }

        public void bind(Record record, SearchRecordAdapter.RecordSelectedListener listener) {
            title.setText(record.getTitle());
            artist.setText(record.getArtist());

            duration.setText(String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(record.getDuration()),
                    TimeUnit.MILLISECONDS.toMinutes(record.getDuration()) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(record.getDuration())), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(record.getDuration()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(record.getDuration()))));

            itemView.setOnClickListener(v -> listener.recordSelected(record));
        }
    }


}
