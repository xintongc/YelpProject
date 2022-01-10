package com.rbc.yelp.viewmodel;

import android.util.Log;

import com.rbc.yelp.services.YelpApi;
import com.rbc.yelp.services.YelpRetrofit;
import com.rbc.yelp.services.models.Business;
import com.rbc.yelp.services.models.Category;
import com.rbc.yelp.services.models.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultViewModel extends ViewModel {

    private Map<String, List<Business>> categoryMap;
//    private List<Business> businessList;
    private MutableLiveData<List<Business>> businessLiveData = new MutableLiveData<>();
    private String TAG = "Search";

    public void searchYelpApi(String term, String location) {
//        businessList = new ArrayList<>();

        new YelpRetrofit()
                .getRetrofitInstance()
                .create(YelpApi.class)
                .search(term, location).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                Log.w(TAG, "onResponse");
                if (response.body() == null) {
                    Log.w(TAG, "No valid response");
                    return;
                }
//                businessList.addAll(response.body().getBusinesses());
                businessLiveData.postValue(response.body().getBusinesses());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.w(TAG, "OnFailure");
            }
        });
    }

    public MutableLiveData<List<Business>> getBusinessLiveData() {
        return businessLiveData;
    }

    public void buildCategoryMap(List<Business> businessList) {
        categoryMap = new HashMap<>();
        for (Business business : businessList) {
            List<Category> categoryList = business.getCategories();
            for (Category category : categoryList) {
                if (categoryMap.get(category.getTitle()) == null) {
                    categoryMap.put(category.getTitle(), new ArrayList<>());
                }
                categoryMap.get(category.getTitle()).add(business);
            }
        }
    }

    public List<String> getCategoryList() {
        List<String> categoryList = new ArrayList(categoryMap.keySet());
        Collections.sort(categoryList);
        return categoryList;
    }

    public List<Business> getBusinessList() {
        return businessLiveData.getValue();
    }

    public Map<String, List<Business>> getCategoryMap() {
        return categoryMap;
    }
}
