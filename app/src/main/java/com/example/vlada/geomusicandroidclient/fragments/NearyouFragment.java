package com.example.vlada.geomusicandroidclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vlada.geomusicandroidclient.MainPagerAdapter;
import com.example.vlada.geomusicandroidclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlada on 19.03.2017.
 */

public class NearyouFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        List<View> pages = new ArrayList<>();

        View page = inflater.inflate(R.layout.nearyou_map, null);
        pages.add(page);

        page = inflater.inflate(R.layout.categories, null);
        pages.add(page);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.nearyou_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nearyou_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
