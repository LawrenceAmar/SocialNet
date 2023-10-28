package com.example.socialnet;

import java.util.List; // Import List from java.util

public class SocialNetUsers {
    private String name;
    private String status;
    private String picture;
    private String quote;
    private List<String> friends; // Define a List to store friends

    public SocialNetUsers(String name, String status, String picture, List<String> friends, String quote) {
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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
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
}
