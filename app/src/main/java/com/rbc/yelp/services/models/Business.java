package com.rbc.yelp.services.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Business model from the Yelp v3 API.
 * Update this file to include any fields you feel are missing.
 * @see <a href=https://www.yelp.ca/developers/documentation/v3/business_search>Yelp API Business Search</a>
 */
public class Business {

    @SerializedName("name")
    private String name;
    @SerializedName("categories")
    private List<Category> categories;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("rating")
    private Double rating;

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
