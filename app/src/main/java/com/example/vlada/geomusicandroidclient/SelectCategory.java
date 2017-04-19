package com.example.vlada.geomusicandroidclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.adapters.SelectCategoriesAdapter;
import com.example.vlada.geomusicandroidclient.adapters.SubscribedRecyclerAdapter;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SelectCategory extends AppCompatActivity {

    private SelectCategoriesAdapter adapter;
    private RecyclerView recycler;
    private Button select;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcategory_fragment);

        recycler = (RecyclerView) findViewById(R.id.selectCategoryRecyclerView);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));

        if(adapter == null) {
            adapter = new SelectCategoriesAdapter();
        }

        recycler.setAdapter(adapter);
        fetchCategories();
        Toast.makeText(this, "Select three or more categories", Toast.LENGTH_LONG).show();

        select = (Button) findViewById(R.id.account_selectCategories);
        select.setOnClickListener(v ->{
            if(select.isEnabled()){

            }
        });


    }


    private void fetchCategories() {
        ServiceGenerator.getGeomusicService().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            adapter.add(categories);
                        }, error -> {
                            Log.d("failure", "failure");
                            Toast.makeText(this, "Failed to get Subscribed", Toast.LENGTH_SHORT).show();
                        });
    }
}
