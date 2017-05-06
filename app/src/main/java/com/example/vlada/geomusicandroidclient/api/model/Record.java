package com.example.vlada.geomusicandroidclient.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;



public class Record {

    @SerializedName("Title")
    private String title;
    @SerializedName("Artist")
    private String artist;
    @SerializedName("Description")
    private String description;
    @SerializedName("AddDate")
    private String addDate;
    @SerializedName("Url")
    private String url;
    @SerializedName("Duration")
    private Integer duration;
    @SerializedName("Lat")
    private Integer lat;
    @SerializedName("Long")
    private Integer lng;
    @SerializedName("Image")
    private Object image;
    @SerializedName("id")
    private Integer id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLong() {
        return lng;
    }

    public void setLong(Integer _long) {
        this.lng = _long;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
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