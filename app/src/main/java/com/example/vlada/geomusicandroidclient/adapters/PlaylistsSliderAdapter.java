package com.example.vlada.geomusicandroidclient.adapters;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vlada.geomusicandroidclient.PlaylistCoordinatorActivity;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class PlaylistsSliderAdapter extends PagerAdapter {

    private List<Playlist> playlists;

    public PlaylistsSliderAdapter() {
        playlists = new ArrayList<>();
    }

    public void add(List<Playlist> items) {
        playlists.clear();
        playlists.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {


        final LayoutInflater inflater = LayoutInflater.from(collection.getContext());

        View imageLayout = inflater.inflate(R.layout.item_palylist_slider, collection, false);
        ImageView image = (ImageView) imageLayout.findViewById(R.id.itemSliderImage);
        TextView title = (TextView) imageLayout.findViewById(R.id.itemSliderTitle);
        TextView desc = (TextView) imageLayout.findViewById(R.id.itemSliderDesc);
        ImageView background = (ImageView) imageLayout.findViewById(R.id.sliderBackground);

        Playlist p = playlists.get(position);
        image.setImageResource(p.getId());
        title.setText(p.getTitle());
        desc.setText(p.getDescription());
        Bitmap bm = BitmapFactory.decodeResource(imageLayout.getResources(), p.getId());
        Blurry.with(imageLayout.getContext()).radius(10).sampling(8).async().from(bm).into(background);

        imageLayout.setOnClickListener(c -> {
            Intent intent = new Intent(imageLayout.getContext(), PlaylistCoordinatorActivity.class);
            imageLayout.getContext().startActivity(intent);
        });


        collection.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
