package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookData{

    @JsonProperty
    String name;

    @JsonProperty
    String status;

    public BookData() {
    }

    public BookData(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookData{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
