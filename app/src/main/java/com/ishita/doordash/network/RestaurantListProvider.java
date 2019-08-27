package com.ishita.doordash.network;

import com.ishita.doordash.interfaces.RestaurantListContract;
import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.utils.LogUtils;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantListProvider implements RestaurantListContract.Model {

    private static final String TAG = "RestaurantListProvider";

    /**
     * This function will fetch restaurant data
     *
     * @param onFinishedListener
     */
    @Override
    public void getRestaurantList(ApiClient apiClient, final OnFinishedListener onFinishedListener, double lat, double lng) {
        Call<List<Restaurant>> call = apiClient.getRestaurants(lat, lng);

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                List<Restaurant> restaurants = response.body();
                Collections.sort(restaurants);
                if (LogUtils.DEBUG) {
                    LogUtils.d(TAG, "List of Restaurants received: " + restaurants.toString());
                }
                onFinishedListener.onFinished(restaurants);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                // Log error here since request failed
                if (LogUtils.DEBUG) {
                    LogUtils.e(TAG, "Restaurants List Exception: " + t.toString());
                }
                onFinishedListener.onFailure(t);
            }
        });
    }
}
