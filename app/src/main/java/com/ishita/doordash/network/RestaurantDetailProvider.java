package com.ishita.doordash.network;

import com.ishita.doordash.interfaces.RestaurantDetailContract;
import com.ishita.doordash.model.RestaurantDetails;
import com.ishita.doordash.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailProvider implements RestaurantDetailContract.Model {
    private static final String TAG = "DetailProvider";

    @Override
    public void getRestaurantDetails(ApiClient apiClient, final OnFinishedListener onFinishedListener, String restaurantId) {
        apiClient
                .getRestaurantDetails(restaurantId)
                .enqueue(new Callback<RestaurantDetails>() {
                    @Override
                    public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                        RestaurantDetails restaurantDetails = response.body();
                        if (LogUtils.DEBUG) {
                            LogUtils.d(TAG, "Restaurant details data received: " + restaurantDetails.toString());
                        }
                        onFinishedListener.onFinished(restaurantDetails);
                    }

                    @Override
                    public void onFailure(Call<RestaurantDetails> call, Throwable t) {
                        if (LogUtils.DEBUG) {
                            LogUtils.e(TAG, "Restaurant details Exception: " + t.toString());
                        }
                        onFinishedListener.onFailure(t);
                    }
                });

    }
}