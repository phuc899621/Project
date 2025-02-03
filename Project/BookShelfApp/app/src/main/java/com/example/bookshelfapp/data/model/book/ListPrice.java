package com.example.bookshelfapp.data.model.book;

import com.google.gson.annotations.SerializedName;

public class ListPrice {
    @SerializedName("amount")
    private int amount;
    @SerializedName("currencyCode")
    private String currencyCode;

    public ListPrice(int amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
