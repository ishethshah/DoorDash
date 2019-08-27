package com.ishita.doordash.presenter;

import com.ishita.doordash.interfaces.RestaurantListContract;
import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.network.ApiClient;
import com.ishita.doordash.network.RestaurantListProvider;

import java.util.List;

public class RestaurantListPresenter implements
        RestaurantListContract.Presenter,
        RestaurantListContract.Model.OnFinishedListener {

    private RestaurantListContract.View restaurantListView;
    private RestaurantListContract.Model restaurantListModel;

    public RestaurantListPresenter(RestaurantListContract.View restaurantListView) {
        this.restaurantListView = restaurantListView;
        this.restaurantListModel = new RestaurantListProvider();
    }

    @Override
    public void requestDataFromServer(double lat, double lng) {
        if (restaurantListView != null) {
            restaurantListView.showProgress();
            restaurantListModel.getRestaurantList(getApiClient(), this, lat, lng);
        }
    }

    @Override
    public void onFinished(List<Restaurant> restaurantsArrayList) {
        if (restaurantListView != null) {
            restaurantListView.setDataToRecyclerView(restaurantsArrayList);
            restaurantListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (restaurantListView != null) {
            restaurantListView.onResponseFailure(t);
            restaurantListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.restaurantListView = null;
    }

    private ApiClient getApiClient() {
        return ApiClient.getInstance();
    }
}
