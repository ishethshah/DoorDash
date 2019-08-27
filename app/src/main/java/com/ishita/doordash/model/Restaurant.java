package com.ishita.doordash.model;

import com.google.gson.annotations.SerializedName;

public class Restaurant implements Comparable<Restaurant> {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("cover_img_url")
    private String coverImageUrl;

    @SerializedName("status")
    private String status;

    //kept as integer as delivery fee will not be more than max int
    @SerializedName("delivery_fee")
    private int deliveryFee;

    @SerializedName("average_rating")
    private float rating;

    public Restaurant(long id, String name, String description, String coverImageUrl, String status, int deliveryFee, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.status = status;
        this.deliveryFee = deliveryFee;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public int compareTo(Restaurant compareRestaurant) {

        int compareDeliveryFee = ((Restaurant) compareRestaurant).getDeliveryFee();

        //ascending order
        return this.deliveryFee - compareDeliveryFee;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                ", status='" + status + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", rating=" + rating +
                '}';
    }
}
