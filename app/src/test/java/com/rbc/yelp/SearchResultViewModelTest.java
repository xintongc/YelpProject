package com.rbc.yelp;
import com.rbc.yelp.viewmodel.SearchResultViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SearchResultViewModelTest{

    @Mock
    private SearchResultViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testBusinessList() {
        viewModel.getBusinessLiveData();
        Mockito.verify(viewModel, times(1)).getBusinessLiveData();
    }


}














