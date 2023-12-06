package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderProductApiResponse {

    @SerializedName("carts")
    private List<Product> orderProducts;

    public List<Product> getProductsInOrder() {
        return orderProducts;
    }
}
