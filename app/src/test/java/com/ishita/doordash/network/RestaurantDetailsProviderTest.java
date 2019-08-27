package com.ishita.doordash.network;

import com.ishita.doordash.interfaces.RestaurantDetailContract.Model.OnFinishedListener;
import com.ishita.doordash.model.RestaurantDetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import retrofit2.mock.Calls;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RestaurantDetailsProviderTest {

    private RestaurantDetailProvider restaurantDetailProvider;

    @Mock
    private ApiClient restaurantApi;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        restaurantDetailProvider = new RestaurantDetailProvider();
    }

    @After
    public void tearDown() {
        restaurantDetailProvider = null;
    }

    @Test
    public void test_getRestaurantDetails() {
        OnFinishedListener listener = mock(OnFinishedListener.class);

        RestaurantDetails restaurant = mock(RestaurantDetails.class);
        when(restaurantApi.getRestaurantDetails(anyString()))
                .thenReturn(Calls.response(restaurant));

        restaurantDetailProvider.getRestaurantDetails(restaurantApi, listener, "asdfsdfsdfs");
        verify(listener).onFinished(restaurant);
    }
}