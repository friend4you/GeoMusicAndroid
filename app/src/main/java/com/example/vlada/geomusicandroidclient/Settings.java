package com.example.vlada.geomusicandroidclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.vlada.geomusicandroidclient.adapters.CategoryRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.model.Category;

import java.util.List;

public class Settings extends AppCompatActivity {

    private RecyclerView recycler;
    private Toolbar toolbar;
    private CategoryRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        recycler = (RecyclerView) findViewById(R.id.settingsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        if(adapter == null){
            adapter = new CategoryRecyclerAdapter();
        }
        recycler.setAdapter(adapter);
        List<Category> categoryList = Application.getSharedInstance().getStorage().getSettingsCategories();
        adapter.add(categoryList);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

    }


}
