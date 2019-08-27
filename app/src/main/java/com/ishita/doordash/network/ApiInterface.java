package com.ishita.doordash.network;

import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.model.RestaurantDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/v2/restaurant/")
    Call<List<Restaurant>> getRestaurants(@Query("lat") double lat,
                                          @Query("lng") double lng,
                                          @Query("offset") int offset,
                                          @Query("limit") int limit);

    @GET("/v2/restaurant/{restaurant_id}/")
    Call<RestaurantDetails> getRestaurantDetails(@Path("restaurant_id") String restaurantId);

}
