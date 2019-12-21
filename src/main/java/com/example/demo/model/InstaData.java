package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstaData{

    @JsonProperty
    private String userName;

    @JsonProperty
    String picture;

    public InstaData() {
    }

    public InstaData(String userName, String picture) {
        this.userName = userName;
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "InstaData{" +
                "userName='" + userName + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
