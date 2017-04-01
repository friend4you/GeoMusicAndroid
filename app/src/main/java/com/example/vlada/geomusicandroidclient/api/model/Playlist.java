package com.example.vlada.geomusicandroidclient.api.model;


import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playlist {

    private Category Category;
    private List<Record> Records = null;
    private String Image;
    private String Title;
    private String Description;
    private String Author;
    private Integer id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        this.Category = category;
    }

    public List<Record> getRecords() {
        return Records;
    }

    public void setRecords(List<Record> records) {
        this.Records = records;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}