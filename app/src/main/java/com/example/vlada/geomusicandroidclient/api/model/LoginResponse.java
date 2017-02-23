package com.example.vlada.geomusicandroidclient.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

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
    @SerializedName("Name")
    private Object name;
    @SerializedName("Surnname")
    private Object surnname;
    @SerializedName("VkToken")
    private Object vkToken;
    @SerializedName("GoogleToken")
    private Object googleToken;
    @SerializedName("Email")
    private String email;
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

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public List<Object> getClaims() {
        return claims;
    }

    public void setClaims(List<Object> claims) {
        this.claims = claims;
    }

    public List<Object> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Object> favorites) {
        this.favorites = favorites;
    }

    public List<Object> getLogins() {
        return logins;
    }

    public void setLogins(List<Object> logins) {
        this.logins = logins;
    }

    public List<Object> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Object> playlists) {
        this.playlists = playlists;
    }

    public List<Object> getRoles() {
        return roles;
    }

    public void setRoles(List<Object> roles) {
        this.roles = roles;
    }

    public List<Object> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(List<Object> subscribe) {
        this.subscribe = subscribe;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getSurnname() {
        return surnname;
    }

    public void setSurnname(Object surnname) {
        this.surnname = surnname;
    }

    public Object getVkToken() {
        return vkToken;
    }

    public void setVkToken(Object vkToken) {
        this.vkToken = vkToken;
    }

    public Object getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(Object googleToken) {
        this.googleToken = googleToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public void setSecurityStamp(String securityStamp) {
        this.securityStamp = securityStamp;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Object getLockoutEndDateUtc() {
        return lockoutEndDateUtc;
    }

    public void setLockoutEndDateUtc(Object lockoutEndDateUtc) {
        this.lockoutEndDateUtc = lockoutEndDateUtc;
    }

    public Boolean getLockoutEnabled() {
        return lockoutEnabled;
    }

    public void setLockoutEnabled(Boolean lockoutEnabled) {
        this.lockoutEnabled = lockoutEnabled;
    }

    public Integer getAccessFailedCount() {
        return accessFailedCount;
    }

    public void setAccessFailedCount(Integer accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}