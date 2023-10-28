package com.example.socialnet;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SocialNetUsers {
    private String name;
    private String status;
    private String picture;
    private String quote;
    private String friends;

    public SocialNetUsers(String name, String status, String picture, String friends, String quote) {
        this.name = name;
        this.status = status;
        this.picture = picture;
        this.friends = friends;
        this.quote = quote;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public void setQuote(String quote) {
        this.quote = quote;
    }
    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getName() {
        return name;
    }
    public String getStatus() {
        return status;
    }
    public String getPicture() {
        return picture;
    }
    public String getQuote() {
        return quote;
    }
    public String getFriends() {
        return friends;
    }
}
