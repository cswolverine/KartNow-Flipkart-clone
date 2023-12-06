package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {


    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("status")
    private String orderDateStatus;
    @SerializedName("name")
    private String userName;
    @SerializedName("address")
    private String shippingAddress;
    @SerializedName("phone")
    private String shippingPhone;
    @SerializedName("total_price")
    private int totalPrice;



    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderDateStatus() {
        return orderDateStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
}
