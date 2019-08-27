package com.ishita.doordash.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ishita.doordash.R;
import com.ishita.doordash.model.Restaurant;
import com.ishita.doordash.utils.DoorDashUtils;
import com.ishita.doordash.view.RestaurantListActivity;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantItemHolder> {

    private RestaurantListActivity restaurantListActivity;
    private List<Restaurant> restaurantList;


    public RestaurantListAdapter(RestaurantListActivity restaurantListActivity, List<Restaurant> restaurantList) {
        this.restaurantListActivity = restaurantListActivity;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item_view, parent, false);

        return new RestaurantItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantItemHolder holder, final int position) {

        Restaurant restaurant = restaurantList.get(position);

        holder.restaurantTitle.setText(restaurant.getName());
        holder.restaurantDescription.setText(restaurant.getDescription());
        holder.restaurantStatus.setText(restaurant.getStatus());
        holder.restaurantRating.setText(String.valueOf(restaurant.getRating()));
        if (restaurant.getDeliveryFee() == 0) {
            holder.restaurantDeliveryTag.setText(restaurantListActivity.getString(R.string.restaurant_delivery_free_tag));
        } else {
            holder.restaurantDeliveryFee.setText(DoorDashUtils.convertCentToDollar(restaurant.getDeliveryFee()));
        }

        if (restaurant.getCoverImageUrl() == null) {
            holder.pbLoadImage.setVisibility(View.GONE);
            holder.restaurantCoverImage.setVisibility(View.INVISIBLE);
        } else {
            // loading album cover using Glide library
            Glide.with(restaurantListActivity)
                    .load(restaurant.getCoverImageUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.pbLoadImage.setVisibility(View.GONE);
                            holder.restaurantCoverImage.setVisibility(View.INVISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.pbLoadImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                    .into(holder.restaurantCoverImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantListActivity.onRestaurantItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    class RestaurantItemHolder extends RecyclerView.ViewHolder {

        TextView restaurantTitle;
        TextView restaurantDescription;
        TextView restaurantStatus;
        ImageView restaurantCoverImage;
        TextView restaurantRating;
        TextView restaurantDeliveryTag;
        TextView restaurantDeliveryFee;
        ProgressBar pbLoadImage;

        RestaurantItemHolder(View itemView) {
            super(itemView);

            restaurantTitle = itemView.findViewById(R.id.tv_rest_list_title);
            restaurantDescription = itemView.findViewById(R.id.tv_rest_list_description);
            restaurantStatus = itemView.findViewById(R.id.tv_rest_list_status);
            restaurantCoverImage = itemView.findViewById(R.id.img_rest_list_pic);
            restaurantRating = itemView.findViewById(R.id.tv_rest_list_rating_number);
            restaurantDeliveryTag = itemView.findViewById(R.id.tv_rest_list_delivery);
            restaurantDeliveryFee = itemView.findViewById(R.id.tv_rest_list_delivery_cost);
            pbLoadImage = itemView.findViewById(R.id.pb_load_image);
        }
    }
}
