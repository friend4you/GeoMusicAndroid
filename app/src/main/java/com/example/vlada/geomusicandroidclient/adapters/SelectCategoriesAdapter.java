package com.example.vlada.geomusicandroidclient.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.events.SelectCategoriesEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class SelectCategoriesAdapter extends Adapter<ViewHolder> {

    private List<Category> items;
    public List<Category> selectedItems;


    public SelectCategoriesAdapter() {
        this.items = new ArrayList<>();
        this.selectedItems = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CategoryViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectcategory_fragment, parent, false);
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
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        void bind(Category category) {
            title.setText(category.getName());
            GradientDrawable drawable = (GradientDrawable) itemView.getBackground();
            final int color = Color.parseColor("#" + category.getColor());
            drawable.setStroke(3, color);
            if (selectedItems.contains(category)) {
                drawable.setColor(color);
                title.setTextColor(Color.WHITE);
            } else {
                drawable.setColor(Color.WHITE);
                title.setTextColor(Color.BLACK);
            }
            String url;
            url = "https://geomusic.blob.core.windows.net/" + category.getImage();
            Glide.with(image.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);

            itemView.setOnClickListener(v -> {
                if (selectedItems.contains(category)) {
                    selectedItems.remove(category);

                } else {
                    selectedItems.add(category);
                }
                EventBus.getDefault().post(new SelectCategoriesEvent(selectedItems));
                notifyItemChanged(items.indexOf(category));

            });
        }
    }
}