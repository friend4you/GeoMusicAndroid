package com.example.vlada.geomusicandroidclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.events.PlayRecordEvent;
import com.example.vlada.geomusicandroidclient.events.ShowRecordTitleEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class PlaylistCoordinatorActivity extends AppCompatActivity {

    private List<Record> records = new ArrayList<>();
    private RecyclerView recycler;
    private SearchRecordAdapter recyclerAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_coordinator);

        Intent intent = getIntent();
        String title;
        if(intent == null){
            title = "Default";
        }else{
            title = intent.getStringExtra("Title");
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


        records = Application.getSharedInstance().getStorage().getRecords();
        recycler = (RecyclerView) findViewById(R.id.coordinator_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        if (recyclerAdapter == null) {
            recyclerAdapter = new SearchRecordAdapter(records, record -> {
                EventBus.getDefault().post(new ShowRecordTitleEvent(record));
                EventBus.getDefault().post(new PlayRecordEvent(record));
            });
        }

        recycler.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();


    }
}
