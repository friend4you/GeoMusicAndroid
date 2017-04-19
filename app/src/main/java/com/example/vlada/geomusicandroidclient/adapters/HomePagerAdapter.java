package com.example.vlada.geomusicandroidclient.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.fragments.CategoryController;
import com.example.vlada.geomusicandroidclient.fragments.HomeController;
import com.example.vlada.geomusicandroidclient.fragments.TrendingController;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends PagerAdapter {

    enum HomeScreens {
        HOME, TRENDING, CATEGORIES
    }


    private final List<HomeScreens> screens;

    public HomePagerAdapter() {
        screens = new ArrayList<HomeScreens>() {{
            add(HomeScreens.HOME);
            add(HomeScreens.TRENDING);
            add(HomeScreens.CATEGORIES);
        }};

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Playlists";
            case 1:
                return "Trending";
            case 2:
                return "Categories";
        }
        return "default";
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final LayoutInflater inflater = LayoutInflater.from(collection.getContext());
        View view = null;
        Object object = null;
        switch (HomeScreens.values()[position]){
            case HOME:
                View home = inflater.inflate(R.layout.playlists_featured, collection, false);
                object = new HomeController(home);
                collection.addView(home);
                break;
            case TRENDING:
                View trend = inflater.inflate(R.layout.playlists_trending, collection, false);
                object = new TrendingController(trend);
                collection.addView(trend);
                break;
            case CATEGORIES:
                View cat = inflater.inflate(R.layout.categories, collection, false);
                object = new CategoryController(cat);
                collection.addView(cat);
                break;
        }
        collection.setTag(object);
        return object;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeViewAt(position);
        Log.d("delete view" , position+"");
    }

    @Override
    public int getCount() {
        return screens.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.getTag() == object;
    }


}