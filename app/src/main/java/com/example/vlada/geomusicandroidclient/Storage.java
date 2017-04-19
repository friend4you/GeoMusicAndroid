package com.example.vlada.geomusicandroidclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;
import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.api.model.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Storage {
    private final SharedPreferences sharedPreferences;
    private final String PREFERENCE_NAME = "settings";
    private final String IS_AUTHORIZED_TAG = "is_authorized";
    private final String EMAIL_TAG = "email";
    private final String PASSWORD = "password";
    private final String RECORD_ID_TAG = "record_id";
    private final String RECORD_LOACTION_TAG = "record_location";
    private final String LOCAL_TAG = "local";
    private final String SERVER_TAG = "server";

    public Storage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_AUTHORIZED_TAG, false);
    }

    public void setLogin(boolean login) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putBoolean(IS_AUTHORIZED_TAG, login);
        ed.apply();
    }

    public void logout() {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putBoolean(IS_AUTHORIZED_TAG, false);
        ed.putString(EMAIL_TAG, "");
        ed.putString(PASSWORD, "");
        ed.apply();
    }

    public void saveLastSong(Record record) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(RECORD_ID_TAG, record.getId());
        if (record.getDate() == null) {
            ed.putString(RECORD_LOACTION_TAG, LOCAL_TAG);
        } else {
            ed.putString(RECORD_LOACTION_TAG, SERVER_TAG);
        }
        ed.apply();
    }

    public int getLastRecordId() {
        return sharedPreferences.getInt("record_id", -1);
    }

    public void saveUserInfo(User user) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("TOKEN", user.getToken());
        ed.putString("USER_ID", user.getId());
        ed.putString("USER_NICKNAME", user.getUserName());
        ed.putString("USER_IMAGE", user.getUserImage());
        ed.putString(EMAIL_TAG, user.getEmail());
        ed.apply();
    }

    public List<Playlist> getPlaylists(){
        List<Playlist> playlists = new ArrayList<>();

        Playlist p = new Playlist();

        p.setId(R.drawable.city);
        p.setTitle("City madness");
        p.setAuthor("Harry Potter");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.retro);
        p.setTitle("Awesome mix vol.1");
        p.setAuthor("Star Lord");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.soft);
        p.setTitle("Space gym");
        p.setAuthor("Jason Statham");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.guide);
        p.setTitle("Dance Commander");
        p.setAuthor("Charlize Theron");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.lyrics);
        p.setTitle("Dance Pattern");
        p.setAuthor("James McAvoy");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.piano);
        p.setTitle("Piano dogs");
        p.setAuthor("Scarlett Johansson");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.punkpop);
        p.setTitle("There's Something Very Wrong With Us");
        p.setAuthor("James Gunn");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.ragg);
        p.setTitle("Bob Marley legacy");
        p.setAuthor("Paul Walker");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.study);
        p.setTitle("To infinity and beyond");
        p.setAuthor("Morgan Freeman");
        playlists.add(p);

        return playlists;
    }

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();

        Category c = new Category();
        c.setColor("#0e2f44");
        c.setId(R.drawable.acoustic);
        c.setName("Acoustic");
        categories.add(c);

        c.setColor("#8c0008");
        c.setId(R.drawable.blues);
        c.setName("Blues");
        categories.add(c);

        c.setColor("#3f583e");
        c.setId(R.drawable.clasical);
        c.setName("Clasical");
        categories.add(c);

        c.setColor("##2f4f4f");
        c.setId(R.drawable.country);
        c.setName("Country");
        categories.add(c);

        c.setColor("#483d8b");
        c.setId(R.drawable.disco);
        c.setName("Disco");
        categories.add(c);

        c.setColor("#68228b");
        c.setId(R.drawable.edm);
        c.setName("Eelctro");
        categories.add(c);

        c.setColor("#556b2f");
        c.setId(R.drawable.eighties);
        c.setName("80's");
        categories.add(c);

        c.setColor("#458b00");
        c.setId(R.drawable.hip_hop);
        c.setName("Hip-Hop");
        categories.add(c);

        c.setColor("#458b74");
        c.setId(R.drawable.indie);
        c.setName("Indie");
        categories.add(c);

        c.setColor("#8b864e");
        c.setId(R.drawable.jazz);
        c.setName("Jazz");
        categories.add(c);

        c.setColor("#8b8970");
        c.setId(R.drawable.k_pop);
        c.setName("K-pop");
        categories.add(c);

        c.setColor("#68838b");
        c.setId(R.drawable.latin);
        c.setName("Latin");
        categories.add(c);

        c.setColor("#8b5f65");
        c.setId(R.drawable.metal);
        c.setName("Metal");
        categories.add(c);

        c.setColor("#5d478b");
        c.setId(R.drawable.nineth);
        c.setName("90's");
        categories.add(c);

        c.setColor("#191970");
        c.setId(R.drawable.pop);
        c.setName("Pop");
        categories.add(c);

        c.setColor("#8b5a00");
        c.setId(R.drawable.punk);
        c.setName("Punk");
        categories.add(c);

        c.setColor("#8b2500");
        c.setId(R.drawable.rb);
        c.setName("R&B");
        categories.add(c);

        c.setColor("#8b4789");
        c.setId(R.drawable.reggae);
        c.setName("Reggae");
        categories.add(c);

        c.setColor("#548b54");
        c.setId(R.drawable.rock);
        c.setName("Rock");
        categories.add(c);

        c.setColor("#668b8b");
        c.setId(R.drawable.vocal);
        c.setName("Vocal");
        categories.add(c);

        return  categories;
    }

    public String getUserToken() {
        return sharedPreferences.getString("TOKEN", "0");
    }

    public String getUserId() {
        return sharedPreferences.getString("USER_ID", "0");
    }

    public String getUserNickname() {
        return sharedPreferences.getString("USER_NICKNAME", "DefaultUserName");
    }

    public String getUserImage() {
        return sharedPreferences.getString("USER_IMAGE", "Default");

    }
    public String getUserEmail(){
        return sharedPreferences.getString(EMAIL_TAG, "@default@domain.com");
    }
}
