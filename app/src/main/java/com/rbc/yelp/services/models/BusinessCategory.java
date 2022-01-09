package com.rbc.yelp.services.models;

import java.util.List;

public class BusinessCategory {
    private String alias;
    private String title;
    private List<Business> businesses;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public void addBusiness(Business business) {
        businesses.add(business);
    }

    public int getBusinessNum() {
        return businesses.size();
    }
}
