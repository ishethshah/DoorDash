package com.ishita.doordash.network;

import com.ishita.doordash.BuildConfig;
import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.model.RestaurantDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final int LIMIT = 50;
    public static final int OFFSET = 0;
    private static ApiClient sInstance = new ApiClient();
    private ApiInterface apiInterface;

    private ApiClient() {
        apiInterface = new Retrofit.Builder()
                .baseUrl(BuildConfig.DOOR_DASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);

    }

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    public static ApiClient getInstance() {
        return sInstance;
    }

    public Call<RestaurantDetails> getRestaurantDetails(String restaurantId) {
        return apiInterface.getRestaurantDetails(restaurantId);
    }

    public Call<List<Restaurant>> getRestaurants(double lat, double lon) {
        return apiInterface.getRestaurants(lat, lon, OFFSET, LIMIT);
    }
}
