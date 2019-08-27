package com.ishita.doordash.interfaces;

import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.network.ApiClient;

import java.util.List;

public interface RestaurantListContract {

    interface Model {

        void getRestaurantList(ApiClient apiClient, OnFinishedListener onFinishedListener,
                               double lat, double lng);

        interface OnFinishedListener {
            void onFinished(List<Restaurant> restaurantArrayList);

            void onFailure(Throwable t);
        }

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Restaurant> restaurantArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void requestDataFromServer(double lat, double lng);

    }
}
