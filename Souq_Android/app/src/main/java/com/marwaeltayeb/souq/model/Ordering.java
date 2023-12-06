package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Ordering {

    @SerializedName("name_on_card")
    private String nameOnCard;
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("expiration_date")
    private String fullDate;
    @SerializedName("userId")
    private int userId;
    @SerializedName("totalPrice")
    private int totalPrice;

    @SerializedName("ordering_id")
    private int orderingId;

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int totalPrice,int orderingId) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderingId = orderingId;
    }
}



