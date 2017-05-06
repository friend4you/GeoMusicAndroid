package com.example.vlada.geomusicandroidclient.events;

import com.example.vlada.geomusicandroidclient.api.model.Category;

import java.util.List;

/**
 * Created by vlada on 03.05.2017.
 */

public class SelectCategoriesEvent {
    public final List<Category> categoryList;

    public SelectCategoriesEvent(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Category> getCategoryList(){
        return categoryList;
    }
}
