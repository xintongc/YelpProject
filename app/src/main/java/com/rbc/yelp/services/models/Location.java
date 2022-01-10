package com.rbc.yelp.services.models;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("address1")
    private String address1;
    @SerializedName("zip_code")
    private String zip_code;

    public String getAddress1() {
        return address1;
    }

    public String getZip_code() {
        return zip_code;
    }
}
