package com.example.vlada.geomusicandroidclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.adapters.SelectCategoriesAdapter;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.events.SelectCategoriesEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SelectCategory extends AppCompatActivity {

    private SelectCategoriesAdapter adapter;
    private RecyclerView recycler;
    private Button select;
    private TextView selectInfo;
    private List<Category> selectedCategories;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcategory_fragment);

        selectedCategories = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select categories");
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        recycler = (RecyclerView) findViewById(R.id.selectCategoryRecyclerView);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));

        if (adapter == null) {
            adapter = new SelectCategoriesAdapter();
        }

        recycler.setAdapter(adapter);
        //adapter.add(Application.getSharedInstance().getStorage().getCategories());
        fetchCategories();
        Toast.makeText(this, "Select three or more categories", Toast.LENGTH_LONG).show();

        selectInfo = (TextView) findViewById(R.id.selectcategory_info);
        selectInfo.setVisibility(View.VISIBLE);

        select = (Button) findViewById(R.id.selectcategory_selectbutton);
        select.setVisibility(View.INVISIBLE);
        select.setOnClickListener(v -> {
            Log.d("select", "tap");

            for(Category cat : selectedCategories){
                Log.d("selected category", Integer.toString(cat.getId()));
            }

        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectThreeCategories(SelectCategoriesEvent categories) {
        Log.d("selected items", Integer.toString(categories.categoryList.size()));
        if (categories.categoryList.size() < 3) {
            select.setVisibility(View.INVISIBLE);
            selectInfo.setVisibility(View.VISIBLE);
            return;
        }
        select.setVisibility(View.VISIBLE);
        selectInfo.setVisibility(View.INVISIBLE);
        selectedCategories.clear();
        selectedCategories.addAll(categories.categoryList);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
