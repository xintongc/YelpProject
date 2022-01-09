package com.rbc.yelp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rbc.yelp.services.models.Business;
import com.rbc.yelp.viewmodel.SearchResultViewModel;

import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {

    private SearchResultViewModel mViewModel;
    private EditText mEtLocation;
    private EditText mEtSearchTerm;
    private Button mBtSearch;
    private LinearLayout mLlSearchReultList;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchResultViewModel.class);
        mLlSearchReultList = findViewById(R.id.ll_search_result_list);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mEtLocation = findViewById(R.id.et_location);
        mEtSearchTerm = findViewById(R.id.et_search_term);
        mBtSearch = findViewById(R.id.button);
        mBtSearch.setOnClickListener(v ->
                {
                    hideSoftKeyboard(mEtLocation, this);
                    mViewModel.searchYelpApi(mEtSearchTerm.getText().toString(), mEtLocation.getText().toString());
                }
        );
        mEtLocation.setText(getString(R.string.toronto));
        mViewModel.searchYelpApi(mEtSearchTerm.getText().toString(), mEtLocation.getText().toString());
    }

    @Override
    protected void onResume() {
        mLlSearchReultList.removeAllViews();
        buildViewForSearchYelpApi();
        super.onResume();
    }

    private void buildViewForSearchYelpApi() {
        mViewModel.getBusinessLiveData().observe(this, response -> {
            if(response != null) {
                mViewModel.buildCategoryMap(mViewModel.getBusinessList());
                buildViewForCategory();
            }
        });
    }

    private void buildViewForCategory() {
        mLlSearchReultList.removeAllViews();
        List<String> categoryList = mViewModel.getCategoryList();
        Map<String, List<Business>> categoryMap = mViewModel.getCategoryMap();
        for (String category : categoryList) {
            View view = inflater.inflate(R.layout.row_category_header, mLlSearchReultList, false);
            String num = String.valueOf(categoryMap.get(category).size());
            ((TextView) view.findViewById(R.id.tv_category)).setText(category + getString(R.string.category_title) + num);
            mLlSearchReultList.addView(view);

            if (categoryMap != null && categoryMap.get(category) != null) {
                buildViewForBusiness(categoryMap.get(category));
            }
        }
    }


    private void buildViewForBusiness(List<Business> businessList) {
        for (Business bussiness : businessList) {
            View view = inflater.inflate(R.layout.row_business, mLlSearchReultList, false);
            ((TextView) view.findViewById(R.id.tv_business)).setText(bussiness.getName());
            ((TextView) view.findViewById(R.id.tv_business_category)).setText(getString(R.string.category) + bussiness.getCategories().toString());
            ((RatingBar) view.findViewById(R.id.ratingBar)).setRating(bussiness.getRating().floatValue());
            Glide.with(view.getContext()).load(bussiness.getImageUrl()).into((ImageView) view.findViewById(R.id.imageView));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TextView) view.findViewById(R.id.tv_business_category)).setVisibility(View.VISIBLE);
                    ((ImageView) view.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                    ((RatingBar) view.findViewById(R.id.ratingBar)).setVisibility(View.VISIBLE);
                }
            });
            mLlSearchReultList.addView(view);
        }
    }

    private void hideSoftKeyboard(View view, Activity mActivity) {
        InputMethodManager inputMethodManager = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
    }


}
