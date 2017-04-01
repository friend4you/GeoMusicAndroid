package com.example.vlada.geomusicandroidclient.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("Categories")
    private List<Object> categories = null;
    @SerializedName("Claims")
    private List<Object> claims = null;
    @SerializedName("Favorites")
    private List<Object> favorites = null;
    @SerializedName("Logins")
    private List<Object> logins = null;
    @SerializedName("Playlists")
    private List<Object> playlists = null;
    @SerializedName("Roles")
    private List<Object> roles = null;
    @SerializedName("Subscribe")
    private List<Object> subscribe = null;
    @SerializedName("Token")
    private String token;
    @SerializedName("ExpireTime")
    private Object expireTime;
    @SerializedName("Email")
    private String email;
    @SerializedName("UserImage")
    private String userImage;
    @SerializedName("EmailConfirmed")
    private Boolean emailConfirmed;
    @SerializedName("PasswordHash")
    private String passwordHash;
    @SerializedName("SecurityStamp")
    private String securityStamp;
    @SerializedName("PhoneNumber")
    private Object phoneNumber;
    @SerializedName("PhoneNumberConfirmed")
    private Boolean phoneNumberConfirmed;
    @SerializedName("TwoFactorEnabled")
    private Boolean twoFactorEnabled;
    @SerializedName("LockoutEndDateUtc")
    private Object lockoutEndDateUtc;
    @SerializedName("LockoutEnabled")
    private Boolean lockoutEnabled;
    @SerializedName("AccessFailedCount")
    private Integer accessFailedCount;
    @SerializedName("Id")
    private String id;
    @SerializedName("UserName")
    private String userName;

    public List<Object> getCategories() {
        return categories;
    }

    public List<Object> getClaims() {
        return claims;
    }

    public List<Object> getFavorites() {
        return favorites;
    }

    public List<Object> getLogins() {
        return logins;
    }

    public List<Object> getPlaylists() {
        return playlists;
    }

    public List<Object> getRoles() {
        return roles;
    }

    public List<Object> getSubscribe() {
        return subscribe;
    }

    public String getToken() {
        return token;
    }

    public Object getExpireTime() {
        return expireTime;
    }

    public String getEmail() {
        return email;
    }

    public String getUserImage(){ return userImage; }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public Object getLockoutEndDateUtc() {
        return lockoutEndDateUtc;
    }

    public Boolean getLockoutEnabled() {
        return lockoutEnabled;
    }

    public Integer getAccessFailedCount() {
        return accessFailedCount;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}