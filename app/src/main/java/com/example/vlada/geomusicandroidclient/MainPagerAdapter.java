package com.example.vlada.geomusicandroidclient;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MainPagerAdapter extends PagerAdapter {

    private final List<View> pages;

    public MainPagerAdapter(List<View> pages) {
        this.pages = pages;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getContentDescription();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View v = pages.get(position);
        collection.addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
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