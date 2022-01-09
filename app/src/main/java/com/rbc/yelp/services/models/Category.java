package com.rbc.yelp.services.models;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
