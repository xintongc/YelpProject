package com.rbc.yelp.repository;

public class YelpRepository {
    private YelpRepository() {
    }
//
//    public static MutableLiveData<SearchResult> searchBusiness(String term, @NonNull String location) throws IOException{
//        MutableLiveData<Response<SearchResult>> liveData = new MutableLiveData<>();
//        Call<SearchResult> resultCall = new YelpRetrofit()
//                .getRetrofitInstance()
//                .create(YelpApi.class)
//                .search(term, location);
//
//        Response<SearchResult> res = resultCall.execute();
//        if(res.code() == 200){
//            liveData.postValue(res);
//        }
//
//    }
}
