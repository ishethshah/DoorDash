package com.ishita.doordash.network;

import com.ishita.doordash.interfaces.RestaurantListContract.Model.OnFinishedListener;
import com.ishita.doordash.mockdata.Data;
import com.ishita.doordash.model.Restaurant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import retrofit2.mock.Calls;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class RestaurantListProviderTest {

    private RestaurantListProvider listModel;
    @Mock
    private ApiClient restaurantApi;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        listModel = new RestaurantListProvider();
    }

    @After
    public void tearDown() throws Exception {
        listModel = null;
    }

    @Test
    public void test_getRestaurantList() {
        OnFinishedListener listener = mock(OnFinishedListener.class);

        List<Restaurant> restaurants = Data.getRestaurantList();
        when(restaurantApi.getRestaurants(anyDouble(), anyDouble()))
                .thenReturn(Calls.response(restaurants));

        listModel.getRestaurantList(restaurantApi, listener, 34.23, 44.12);

        verify(listener).onFinished(restaurants);
    }
}