package com.example.vlada.geomusicandroidclient.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.fragments.CategoryController;
import com.example.vlada.geomusicandroidclient.fragments.HomeController;
import com.example.vlada.geomusicandroidclient.fragments.TrendingController;

import java.util.ArrayList;
import java.util.List;

import static com.example.vlada.geomusicandroidclient.adapters.NearyouPagerAdapter.NearyouScreens.MAP;

public class NearyouPagerAdapter extends PagerAdapter {

    enum NearyouScreens{
        MAP, CATEGORIES
    }

    List<View> pages = new ArrayList<>();


    public NearyouPagerAdapter(List<View> pages) {

        this.pages.clear();
        this.pages.addAll(pages);

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View v = pages.get(position);
        ((ViewPager) collection).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


}
