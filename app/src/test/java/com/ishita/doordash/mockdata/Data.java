package com.ishita.doordash.mockdata;

import com.ishita.doordash.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Restaurant> getRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(0, "test", "test", "",
                "test", 0, 0));
        return restaurants;
    }
}
