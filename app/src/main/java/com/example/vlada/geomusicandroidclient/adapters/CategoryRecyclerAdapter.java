package com.example.vlada.geomusicandroidclient.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends Adapter<ViewHolder> {

    private List<Category> items;

    public CategoryRecyclerAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CategoryViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolders(layoutView);

    }

    public void add(List<Category> itemList) {
        this.items.clear();
        this.items.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((CategoryViewHolders) holder).bind(items.get(position));
    }


    class CategoryViewHolders extends ViewHolder {

        private TextView title;
        private ImageView image;

        CategoryViewHolders(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.category_title);
            image = (ImageView) itemView.findViewById(R.id.category_image);
        }

        void bind(Category category) {
            title.setText(category.getName());
            image.setImageResource(category.getId());
            /*String url;
            url = category.getImage();
            Glide.with(image.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);*/

            itemView.setOnClickListener(v -> {

                Toast.makeText(itemView.getContext(), category.getName(), Toast.LENGTH_SHORT).show();

            });
        }
    }
}
