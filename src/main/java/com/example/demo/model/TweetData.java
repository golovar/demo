package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetData{

    @JsonProperty
    private String userName;

    @JsonProperty
    String tweet;

    public TweetData() {
    }

    public TweetData(String userName, String tweet) {
        this.userName = userName;
        this.tweet = tweet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    @Override
    public String toString() {
        return "TweetData{" +
                "userName='" + userName + '\'' +
                ", tweet='" + tweet + '\'' +
                '}';
    }
}
