package com.example.vlada.geomusicandroidclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vlada.geomusicandroidclient.adapters.RecordViewHolder;
import com.example.vlada.geomusicandroidclient.api.model.Record;

import java.util.List;

public class SearchRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Record> records;
    private final RecordSelectedListener listener;

    public SearchRecordAdapter(List<Record> records, RecordSelectedListener listener) {
        this.records = records;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater from = LayoutInflater.from(parent.getContext());
        return new RecordViewHolder(from.inflate(R.layout.record_search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecordViewHolder) holder).bind(records.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public interface RecordSelectedListener {
        void recordSelected(Record record);
    }
}
