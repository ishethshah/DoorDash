package com.ishita.doordash.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ishita.doordash.R;
import com.ishita.doordash.adapter.RestaurantListAdapter;
import com.ishita.doordash.interfaces.RestaurantItemClickListener;
import com.ishita.doordash.interfaces.RestaurantListContract;
import com.ishita.doordash.interfaces.RestaurantShowEmptyView;
import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.presenter.RestaurantListPresenter;
import com.ishita.doordash.utils.Constants;
import com.ishita.doordash.utils.DoorDashUtils;
import com.ishita.doordash.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity implements RestaurantListContract.View, RestaurantItemClickListener,
        RestaurantShowEmptyView {


    private static final String TAG = "RestaurantListActivity";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private RestaurantListPresenter restaurantListPresenter;
    private RecyclerView rvRestaurantList;
    private List<Restaurant> restaurantsList;
    private RestaurantListAdapter restaurantListAdapter;
    private ProgressBar pbLoading;
    private TextView currentLocationAddress;
    private TextView emptyView;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 2;
    private LinearLayoutManager mLayoutManager;
    private FloatingActionButton btnRefresh;
    // For Location
    private double latitude;
    private double longitude;

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        getSupportActionBar().setTitle(getString(R.string.restaurant_list_title));
        initUI();
        setListeners();
        //Initializing presenter
        restaurantListPresenter = new RestaurantListPresenter(this);
        fetchData();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location lastLocation = task.getResult();
                            latitude = lastLocation.getLatitude();
                            longitude = lastLocation.getLongitude();
                            if (LogUtils.DEBUG) {
                                LogUtils.d(TAG, "getLastLocation:" + latitude + "," + longitude);
                            }
                            setCurrentLocationAddress(latitude, longitude);
                            restaurantListPresenter.requestDataFromServer(latitude, longitude);
                            hideEmptyView();

                        } else {
                            if (LogUtils.DEBUG) {
                                LogUtils.w(TAG, "getLastLocation:exception", task.getException());
                            }
                            showLocationError();
                        }
                    }
                });
    }


    /**
     * This method will initialize the UI components
     */
    private void initUI() {

        rvRestaurantList = findViewById(R.id.rv_rest_list);

        restaurantsList = new ArrayList<>();
        restaurantListAdapter = new RestaurantListAdapter(this, restaurantsList);

        mLayoutManager = new LinearLayoutManager(this);
        rvRestaurantList.setLayoutManager(mLayoutManager);
        rvRestaurantList.setAdapter(restaurantListAdapter);

        pbLoading = findViewById(R.id.pb_loading);

        currentLocationAddress = findViewById(R.id.current_location);
        btnRefresh = findViewById(R.id.fab_refresh);

        emptyView = findViewById(R.id.empty_view);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * This function will contain listeners for all views.
     */
    private void setListeners() {

        rvRestaurantList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvRestaurantList.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                // Handling the infinite scroll
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    loading = true;
                }

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });


    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<Restaurant> restaurants) {
        if (restaurants != null && !restaurants.isEmpty()) {
            hideEmptyView();
            if (LogUtils.DEBUG) {
                LogUtils.d(TAG, "setDataToRecycleViewCalled New Restaurant list count:" + restaurants.size());
            }
            if (!restaurantsList.isEmpty()) {
                restaurantsList.clear();
            }
            restaurantsList.addAll(restaurants);
            restaurantListAdapter.notifyDataSetChanged();
        } else {
            showEmptyView();
        }
    }


    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(this, getString(R.string.communication_error), Toast.LENGTH_LONG).show();
        showEmptyView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        restaurantListPresenter.onDestroy();
    }

    @Override
    public void onRestaurantItemClick(int position) {
        if (position == -1) {
            return;
        }
        Intent detailIntent = new Intent(this, RestaurantDetailActivity.class);
        detailIntent.putExtra(Constants.KEY_RESTAURANT_ID, String.valueOf(restaurantsList.get(position).getId()));
        startActivity(detailIntent);
    }

    @Override
    public void showEmptyView() {
        clearCurrentLocationAddress();
        rvRestaurantList.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideEmptyView() {
        rvRestaurantList.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(RestaurantListActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        startLocationPermissionRequest();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (LogUtils.DEBUG) {
            LogUtils.i(TAG, "onRequestPermissionResult");
        }
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                if (LogUtils.DEBUG) {
                    LogUtils.i(TAG, "User interaction was cancelled.");
                }
                showLocationError();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                showLocationError();
            }
        }
    }

    private void showLocationError() {
        Toast.makeText(RestaurantListActivity.this, getString(R.string.location_error), Toast.LENGTH_SHORT).show();
        hideProgress();
        showEmptyView();

    }

    public void setCurrentLocationAddress(double latitude, double longitude) {
        final String address = DoorDashUtils.getCompleteAddressString(this, latitude, longitude);
        if (address != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    currentLocationAddress.setText(address);
                }
            });

        }
    }

    public void clearCurrentLocationAddress() {
        if (currentLocationAddress != null) {
            currentLocationAddress.setText(" ");
        }
    }

    public void fetchData() {
        clearCurrentLocationAddress();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

}

