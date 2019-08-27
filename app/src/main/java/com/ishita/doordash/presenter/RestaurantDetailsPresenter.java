package com.ishita.doordash.presenter;

import com.ishita.doordash.interfaces.RestaurantDetailContract;
import com.ishita.doordash.model.RestaurantDetails;
import com.ishita.doordash.network.ApiClient;
import com.ishita.doordash.network.RestaurantDetailProvider;

public class RestaurantDetailsPresenter implements RestaurantDetailContract.Presenter, RestaurantDetailContract.Model.OnFinishedListener {

    private RestaurantDetailContract.View restaurantDetailView;
    private RestaurantDetailContract.Model restaurantDetailsModel;

    public RestaurantDetailsPresenter(RestaurantDetailContract.View restaurantDetailView) {
        this.restaurantDetailView = restaurantDetailView;
        this.restaurantDetailsModel = new RestaurantDetailProvider();
    }

    @Override
    public void requestRestaurantData(String restaurantId) {
        if (restaurantDetailView != null) {
            restaurantDetailView.showProgress();
        }
        restaurantDetailsModel.getRestaurantDetails(
                getApiClient(), this, restaurantId);
    }

    @Override
    public void onFinished(RestaurantDetails restaurantDetails) {
        if (restaurantDetailView != null) {
            restaurantDetailView.setDataToViews(restaurantDetails);
            restaurantDetailView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (restaurantDetailView != null) {
            restaurantDetailView.onResponseFailure(t);
            restaurantDetailView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        restaurantDetailView = null;
    }

    public ApiClient getApiClient() {
        return ApiClient.getInstance();
    }
}
