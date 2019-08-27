package com.ishita.doordash.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ishita.doordash.R;
import com.ishita.doordash.interfaces.RestaurantDetailContract;
import com.ishita.doordash.model.RestaurantDetails;
import com.ishita.doordash.presenter.RestaurantDetailsPresenter;
import com.ishita.doordash.utils.Constants;
import com.ishita.doordash.utils.DoorDashUtils;

public class
RestaurantDetailActivity extends AppCompatActivity implements RestaurantDetailContract.View {


    private static final String TAG = "RestaurantDetailActivity";
    private ImageView ivBackdrop;
    private ProgressBar pbLoadBackdrop;
    private TextView tvRestaurantTitle;
    private TextView tvRestaurantAddress;
    private TextView tvRestaurantTags;
    private TextView tvRestaurantRating;
    private TextView tvRestaurantRatingNumber;
    private TextView tvRestaurantDeliveryFee;
    private TextView tvRestaurantDeliveryTag;
    private String restaurantName;


    private RestaurantDetailsPresenter restaurantDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();

        Intent mIntent = getIntent();
        String restaurantId = (String) mIntent.getStringExtra(Constants.KEY_RESTAURANT_ID);

        restaurantDetailsPresenter = new RestaurantDetailsPresenter(this);
        if (restaurantId != null) {
            restaurantDetailsPresenter.requestRestaurantData(restaurantId);
        }

    }

    /**
     * Initializing UI components
     */
    private void initUI() {

        ivBackdrop = findViewById(R.id.iv_backdrop);
        pbLoadBackdrop = findViewById(R.id.pb_load_backdrop);
        tvRestaurantTitle = findViewById(R.id.tv_restaurant_title);
        tvRestaurantTags = findViewById(R.id.tv_rest_tags);
        tvRestaurantAddress = findViewById(R.id.tv_rest_address);
        tvRestaurantRating = findViewById(R.id.tv_rest_ratings);

        tvRestaurantRatingNumber = findViewById(R.id.tv_rest_rating_number);
        tvRestaurantDeliveryTag = findViewById(R.id.tv_rest_delivery);
        tvRestaurantDeliveryFee = findViewById(R.id.tv_rest_delivery_cost);

    }


    @Override
    public void showProgress() {
        pbLoadBackdrop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoadBackdrop.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(RestaurantDetails restaurantDetails) {

        if (restaurantDetails != null) {

            restaurantName = restaurantDetails.getName();
            tvRestaurantTitle.setText(restaurantDetails.getName());
            tvRestaurantAddress.setText(restaurantDetails.getAddress());
            tvRestaurantTags.setText(restaurantDetails.getFoodTags().toString());
            tvRestaurantRating.setText(String.valueOf(restaurantDetails.getRating()));
            tvRestaurantTags.setText(restaurantDetails.getFoodTags().toString());
            tvRestaurantRating.setText(String.valueOf(restaurantDetails.getRating()));
            tvRestaurantRatingNumber.setText("(" + String.valueOf(restaurantDetails.getNumberOfRatings()) + ")");

            if (restaurantDetails.getDeliveryFee() == 0) {
                tvRestaurantDeliveryTag.setText(getString(R.string.restaurant_delivery_free_tag));
            } else {
                tvRestaurantDeliveryFee.setText(DoorDashUtils.convertCentToDollar(restaurantDetails.getDeliveryFee()));
            }

            // loading album cover using Glide library
            Glide.with(this)
                    .load(restaurantDetails.getCoverImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                    .into(ivBackdrop);

        }

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        restaurantDetailsPresenter.onDestroy();
    }

}