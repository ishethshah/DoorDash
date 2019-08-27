package com.ishita.doordash.presenter;

import com.ishita.doordash.interfaces.RestaurantListContract;
import com.ishita.doordash.mockdata.Data;
import com.ishita.doordash.model.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class RestaurantListPresenterTest {

    @Mock
    private RestaurantListContract.View view;

    private RestaurantListPresenter mainPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new RestaurantListPresenter(this.view);
    }

    @Test
    public void fetchValidDataShouldLoadIntoView() {
        List<Restaurant> restaurants = Data.getRestaurantList();
        mainPresenter.onFinished(restaurants);
        verify(view).setDataToRecyclerView(restaurants);
        verify(view).hideProgress();
    }

    @Test
    public void fetchErrorShouldReturnErrorToView() {
        Throwable throwable = new Throwable();
        mainPresenter.onFailure(throwable);
        verify(view).onResponseFailure(throwable);
    }

}
