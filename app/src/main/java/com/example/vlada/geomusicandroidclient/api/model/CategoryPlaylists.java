package com.example.vlada.geomusicandroidclient.api.model;


import java.util.List;

public class CategoryPlaylists {
    private String title;
    private List<Playlist> playlists;

    public CategoryPlaylists(String title, List<Playlist> playlists) {
        this.title = title;
        this.playlists = playlists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
