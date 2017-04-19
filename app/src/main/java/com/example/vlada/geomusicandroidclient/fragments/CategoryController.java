package com.example.vlada.geomusicandroidclient.fragments;


import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.CategoryRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.adapters.SubscribedRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.example.vlada.geomusicandroidclient.api.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;




public class CategoryController {


    private final RecyclerView recycler;
    private CategoryRecyclerAdapter adapter;
    private List<Category> categories;

    public CategoryController(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.categories_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if (adapter == null) {
            adapter = new CategoryRecyclerAdapter();
        }

        recycler.setAdapter(adapter);

        categories = Application.getSharedInstance().getStorage().getCategories();
        if(categories == null){
            categories = new ArrayList<>();
        }

        adapter.add(categories);

    }


    private void fetchData() {

        ServiceGenerator.getGeomusicService().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            adapter.add(categories);
                        }, error -> {
                            Log.d("failure", "failure");
                            Toast.makeText(recycler.getContext(), "Failed to get Subscribed", Toast.LENGTH_SHORT).show();
                        });
    }

    private <T> void initRecycle(T data) {
    }
}
