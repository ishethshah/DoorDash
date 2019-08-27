package com.ishita.doordash.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantDetails {

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private RestaurantAddress address;


    @SerializedName("tags")
    private ArrayList<String> foodTags;

    @SerializedName("average_rating")
    private float rating;

    @SerializedName("number_of_ratings")
    private int numberOfRatings;

    @SerializedName("delivery_fee")
    private int deliveryFee;


    @SerializedName("phone_number")
    private long phonenumber;


    @SerializedName("cover_img_url")
    private String coverImageUrl;

    public RestaurantDetails(String name, RestaurantAddress address, ArrayList<String> foodTags, float rating, int numberOfRatings, int deliveryFee, long phonenumber, String coverImageUrl) {
        this.name = name;
        this.address = address;
        this.foodTags = foodTags;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.deliveryFee = deliveryFee;
        this.phonenumber = phonenumber;
        this.coverImageUrl = coverImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address.getPrintableAddress();
    }

    public void setAddress(RestaurantAddress address) {
        this.address = address;
    }

    public ArrayList<String> getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(ArrayList<String> foodTags) {
        this.foodTags = foodTags;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }


    @Override
    public String toString() {
        return "RestaurantDetails{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", foodTags=" + foodTags +
                ", rating=" + rating +
                ", numberOfRatings=" + numberOfRatings +
                ", deliveryFee=" + deliveryFee +
                ", phonenumber=" + phonenumber +
                ", coverImageUrl='" + coverImageUrl + '\'' +
                '}';
    }

    class RestaurantAddress {

        @SerializedName("printable_address")
        private String printableAddress;

        public String getPrintableAddress() {
            return printableAddress;
        }

        public void setPrintableAddress(String printableAddress) {
            this.printableAddress = printableAddress;
        }

    }


}
