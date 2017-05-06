package com.example.vlada.geomusicandroidclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vlada.geomusicandroidclient.api.model.Category;
import com.example.vlada.geomusicandroidclient.api.model.Playlist;
import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.api.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

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
        if (record.getAddDate() == null) {
            ed.putString(RECORD_LOACTION_TAG, LOCAL_TAG);
        } else {
            ed.putString(RECORD_LOACTION_TAG, SERVER_TAG);
        }
        ed.apply();
    }

    public void saveRangeLatLng(LatLng latLng){
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putLong("LATITUDE", Double.doubleToLongBits(latLng.latitude));
        ed.putLong("LONGITUDE", Double.doubleToLongBits(latLng.longitude));
        ed.apply();
    }

    public LatLng getRangeLatLng(){
        Double latitude = Double.longBitsToDouble(sharedPreferences.getLong("LATITUDE", -1));
        Double longitude = Double.longBitsToDouble(sharedPreferences.getLong("LONGITUDE", -1));
        return new LatLng(latitude, longitude);
    }

    public int getLastRecordId() {
        return sharedPreferences.getInt("record_id", -1);
    }

    public void saveUserCategories(List<Category> categories){
        if(categories == null){
            categories = new ArrayList<>();
        }
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("USER_CATEGORIES", new Gson().toJson(categories));
        ed.apply();

    }

    public void saveUserInfo(User user) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("TOKEN", user.getToken());
        ed.putString("USER_ID", user.getId());
        ed.putString("USER_NICKNAME", user.getUserName());
        ed.putString("USER_IMAGE", user.getUserImage());
        ed.putString(EMAIL_TAG, user.getEmail());
        saveUserCategories(user.getCategories());
        ed.apply();
    }

    public List<Playlist> getPlaylists(){
        List<Playlist> playlists = new ArrayList<>();

        Playlist p = new Playlist();

        p.setId(R.drawable.city);
        p.setTitle("City madness");
        p.setAuthor("Harry Potter");
        p.setDescription("The former Woods member dropped another track from his upcoming fourth album. \"Aboard My Train\" is as endearing as anything he's ever released. The folksy song finds the freewheeling troubadour recounting his travels with jammy abandon. The sizzling guitar solo straight-up rocks. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.retro);
        p.setTitle("Awesome mix vol.1");
        p.setAuthor("Star Lord");
        p.setDescription("Big Thief follow up last year's Masterpiece with more aptly titled material. \"Mythological Beauty\" is just that. Adrianne Lenker writers and sings with reflective grace about trying to connect with her inner child in the battle for self-acceptance. The guitars shimmer and chime as wistfully as her words.");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.soft);
        p.setTitle("Space gym");
        p.setAuthor("Jason Statham");
        p.setDescription("With their first album in over seven years on the way, \"Loving\" is a welcome return for Land of Talk. The Saddle Creek band's latest song is a shiny piece of indie pop with plenty of anxiety bubbling underneath. Given the band's health issues and industry scrap-ups in recent years, they have a lot of be disillusioned about, but rarely does it sound this good. Sharon Van Etten also lends her voice for some extra lovely back-up harmonies. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.guide);
        p.setTitle("Dance Commander");
        p.setAuthor("Charlize Theron");
        p.setDescription("The Folk-punk duo of Harmony Tividad and Cleo Tucke show off their brilliant harmonies with \"It Gets More Blue.\" Their wistful voices cohere the ramshackle melody and raw production. It's an endearing and auspicious look at their highly anticipated sophomore effort. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.lyrics);
        p.setTitle("Dance Pattern");
        p.setAuthor("James McAvoy");
        p.setDescription("Remember that time Fox News foolishly criticized Kendrick Lamar's highly politicized performance at the BET Awards? Well now the joke's on them. Lamar skillfully samples those reporters on \"BLOOD,\" the lead track off his new album. By recontextualizing their critiques within his artful hip-hop sounds, they are revealed to be more foolhardy than ever. As the album's lead off track, it's a worthy way to kick off an hour of amazing rhymes that follow. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.piano);
        p.setTitle("Piano dogs");
        p.setAuthor("Scarlett Johansson");
        p.setDescription("Jazz maestro Kamasi Washington returns with \"Truth\" a thirteen minute long epic. Yet, while it only consists of a few chords, it never gets boring. The saxophone squeals and choral harmonies are warm, intimate and communal. It's an ambitious, yet accessible work of art. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.punkpop);
        p.setTitle("There's Something Very Wrong With Us");
        p.setAuthor("James Gunn");
        p.setDescription("Chris Stapleton's 2015 album Traveller turned the Nashville songwriter into a bonafide country star. This year he plans to return with not one, but two records that are sure to satisfy fans of his soulful brand of Americana. \"Broken Halos\" is the first taste of From A Room: Volume One and it's a twangy ballad that's sure to win over listeners, new and old alike. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.ragg);
        p.setTitle("Bob Marley legacy");
        p.setAuthor("Paul Walker");
        p.setDescription("The suddenly prolific Frank Ocean released his third track of 2017 and it find him pontificating about bicycles. In typical Frank Ocean fashion though, it's about a lot more than that. It's about the way life cycles by, and the freedom and balance bikes often represent. It' a nifty bit of song craft in that something so conventional becomes something inspiring. And it's made all the more stronger by cameos from Jay Z and Tyler the Creator. ");
        playlists.add(p);

        p = new Playlist();
        p.setId(R.drawable.study);
        p.setTitle("To infinity and beyond");
        p.setAuthor("1234 Subscribed");
        p.setDescription("For the past six years, Pa'l Norte has continually grown and never fails to disappoint visitors with its diverse musical offerings and gorgeous natural beauty. This year's headliners included The Killers and Maná. Placebo, M.I.A., The Offspring, Jason Derulo and many more filled out the bill, making for one high energy weekend.");
        playlists.add(p);

        return playlists;
    }

    public List<Record> getRecords(){
        List<Record> records = new ArrayList<>();

        Record r = new Record();
        r.setArtist("The XX");
        r.setTitle("Crystallized");
        r.setDuration(200000);
        r.setUrl("https://cs7-3v4.vk-cdn.net/p12/43d8f2cbe9f53b.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Bag Raiders");
        r.setTitle("Shooting Stars");
        r.setDuration(228000);
        r.setUrl("https://cs7-2v4.vk-cdn.net/p1/04996879d8f5ac.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Kaleo");
        r.setTitle("Way Down We Go");
        r.setDuration(219000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p20/a76700e74b3fb0.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("LALIKO");
        r.setTitle("Sugar Whisper");
        r.setDuration(227000);
        r.setUrl("https://cs7-6v4.vk-cdn.net/p23/1f7cd2e8b66d6e.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Bloodhound Gang");
        r.setTitle("Fire Water Burn");
        r.setDuration(293000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p1/333e702c2970b7.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("ABBA");
        r.setTitle("Gimme! Gimme! Gimme!");
        r.setDuration(288000);
        r.setUrl("https://cs7-2v4.vk-cdn.net/p8/f2202875588823.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Georg Michael");
        r.setTitle("Marry Christmas");
        r.setDuration(267000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p24/40ee951bc1f3d6.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Twenty one pilots");
        r.setTitle("Heathens");
        r.setDuration(195000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p17/fefa45fc3c8b6c.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Flo Rida");
        r.setTitle("My House");
        r.setDuration(192000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p7/5d02e6fb552a44.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Bruno Mars");
        r.setTitle("Runaway baby");
        r.setDuration(138000);
        r.setUrl("https://cs7-3v4.vk-cdn.net/p18/41152c088a1699.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Daftpunk");
        r.setTitle("Get lucky");
        r.setDuration(350000);
        r.setUrl("https://cs7-5v4.vk-cdn.net/p1/f0257ccc107b7a.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Maxence Cyrin");
        r.setTitle("Where Is My Mind");
        r.setDuration(125000);
        r.setUrl("https://cs7-3v4.vk-cdn.net/p14/50c218452c54b2.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Hollywood Principle");
        r.setTitle("Breathing Underwater");
        r.setDuration(277000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p7/9064c50f3e26ec.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("Lana Del Rey");
        r.setTitle("High By The Beach");
        r.setDuration(246000);
        r.setUrl("https://cs7-3v4.vk-cdn.net/p16/35e215f51beead.mp3");
        records.add(r);

        r = new Record();
        r.setArtist("The Weeknd");
        r.setTitle("I Cant Feel My Face");
        r.setDuration(216000);
        r.setUrl("https://cs7-4v4.vk-cdn.net/p10/2f36cb8c5173a3.mp3");
        records.add(r);

        return records;
    }

    public List<Category> getSettingsCategories(){
        List<Category> categories = new ArrayList<>();

        Category c = new Category();
        c.setName("Playback");
        c.setId(R.drawable.ic_play_arrow_black_48dp);
        categories.add(c);

        c = new Category();
        c.setName("Notification");
        c.setId(R.drawable.ic_notifications_black_48dp);
        categories.add(c);

        c = new Category();
        c.setName("General");
        c.setId(R.drawable.ic_settings_black_48dp);
        categories.add(c);

        c = new Category();
        c.setName("Account");
        c.setId(R.drawable.ic_account_box_black_48dp);
        categories.add(c);

        c = new Category();
        c.setName("Help");
        c.setId(R.drawable.ic_help_outline_black_48dp);
        categories.add(c);

        c = new Category();
        c.setName("About");
        c.setId(R.drawable.ic_info_outline_black_48dp);
        categories.add(c);

        c = new Category();
        c.setName("LOG OUT");
        c.setId(R.drawable.logout);
        categories.add(c);

        return categories;
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
