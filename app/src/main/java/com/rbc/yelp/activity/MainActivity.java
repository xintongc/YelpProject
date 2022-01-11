package com.rbc.yelp.activity;

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
import com.rbc.yelp.R;
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
    private LinearLayout mLlSearchResultList;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchResultViewModel.class);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
        mViewModel.searchYelpApi(mEtSearchTerm.getText().toString(), mEtLocation.getText().toString());
    }

    @Override
    protected void onResume() {
        mLlSearchResultList.removeAllViews();
        buildViewForSearchYelpApi();
        super.onResume();
    }

    private void initView() {
        mEtLocation = findViewById(R.id.et_location);
        mEtSearchTerm = findViewById(R.id.et_search_term);
        mBtSearch = findViewById(R.id.button);
        mBtSearch.setOnClickListener(v ->
                {
                    hideSoftKeyboard(mEtLocation, this);
                    mViewModel.searchYelpApi(mEtSearchTerm.getText().toString(), mEtLocation.getText().toString());
                }
        );
        mLlSearchResultList = findViewById(R.id.ll_search_result_list);
        mEtLocation.setText(getString(R.string.toronto));
    }

    private void buildViewForSearchYelpApi() {
        mViewModel.getBusinessLiveData().observe(this, response -> {
            if (response != null) {
                mViewModel.buildCategoryMap(mViewModel.getBusinessList());
                buildViewForCategory();
            }
        });
    }

    private void buildViewForCategory() {
        mLlSearchResultList.removeAllViews();
        List<String> categoryList = mViewModel.getCategoryList();
        Map<String, List<Business>> categoryMap = mViewModel.getCategoryMap();
        for (String category : categoryList) {
            View view = inflater.inflate(R.layout.row_category_header, mLlSearchResultList, false);
            String num = String.valueOf(categoryMap.get(category).size());
            ((TextView) view.findViewById(R.id.tv_category)).setText(category + getString(R.string.colon) + num);
            mLlSearchResultList.addView(view);

            if (categoryMap != null && categoryMap.get(category) != null) {
                buildViewForBusinesses(categoryMap.get(category));
            }
        }
    }


    private void buildViewForBusinesses(List<Business> businessList) {
        for (Business business : businessList) {
            View view = inflater.inflate(R.layout.row_business, mLlSearchResultList, false);
            ((TextView) view.findViewById(R.id.tv_business_name)).setText(business.getName());
            ((TextView) view.findViewById(R.id.tv_address)).setText(getString(R.string.address) + business.getLocation().getAddress1());
            ((TextView) view.findViewById(R.id.tv_business_category)).setText(getString(R.string.category) + business.getCategories().toString().substring(1, business.getCategories().toString().length() - 1));
            ((RatingBar) view.findViewById(R.id.rb_ratingBar)).setRating(business.getRating().floatValue());
            Glide.with(view.getContext()).load(business.getImageUrl()).into((ImageView) view.findViewById(R.id.iv_imageView));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideSoftKeyboard(mEtLocation, MainActivity.this);

                    if (((TextView) view.findViewById(R.id.tv_business_category)).getVisibility() == View.GONE) {
                        ((TextView) view.findViewById(R.id.tv_business_category)).setVisibility(View.VISIBLE);
                        ((TextView) view.findViewById(R.id.tv_address)).setVisibility(View.VISIBLE);
                        ((ImageView) view.findViewById(R.id.iv_imageView)).setVisibility(View.VISIBLE);
                        ((RatingBar) view.findViewById(R.id.rb_ratingBar)).setVisibility(View.VISIBLE);
                    } else {
                        ((TextView) view.findViewById(R.id.tv_business_category)).setVisibility(View.GONE);
                        ((TextView) view.findViewById(R.id.tv_address)).setVisibility(View.GONE);
                        ((ImageView) view.findViewById(R.id.iv_imageView)).setVisibility(View.GONE);
                        ((RatingBar) view.findViewById(R.id.rb_ratingBar)).setVisibility(View.GONE);
                    }
                }
            });
            mLlSearchResultList.addView(view);
        }
    }

    private void hideSoftKeyboard(View view, Activity mActivity) {
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
    }


}
