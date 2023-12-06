package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class OrderedProducts {
    @SerializedName("orderingId")
    private int orderId;
    @SerializedName("productId")
    private int productId;

    public OrderedProducts(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
