package com.ishita.doordash.interfaces;

import com.ishita.doordash.model.RestaurantDetails;
import com.ishita.doordash.network.ApiClient;

public interface RestaurantDetailContract {

    interface Model {

        void getRestaurantDetails(ApiClient apiClient, OnFinishedListener onFinishedListener, String restaurantId);

        interface OnFinishedListener {
            void onFinished(RestaurantDetails restaurantDetails);

            void onFailure(Throwable t);
        }
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(RestaurantDetails restaurantDetails);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestRestaurantData(String restaurantId);
    }
}
