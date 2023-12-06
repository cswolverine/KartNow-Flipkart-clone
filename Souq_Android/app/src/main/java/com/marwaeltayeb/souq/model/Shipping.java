package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("zip")
    private String zip;
    @SerializedName("phone")
    private String phone;
    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;


    @SerializedName("orderingId")
    private int orderNumber;

    public Shipping(String address, String city, String country, String zip, String phone, int userId, int productId, int orderNumber) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.userId = userId;
        this.productId = productId;
        this.orderNumber = orderNumber;
    }
}
