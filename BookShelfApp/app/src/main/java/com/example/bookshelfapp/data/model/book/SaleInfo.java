package com.example.bookshelfapp.data.model.book;

import com.google.gson.annotations.SerializedName;

public class SaleInfo {
    @SerializedName("saleability")
    private String saleability;
    @SerializedName("listPrice")
    private ListPrice listPrice;
    @SerializedName("buyLink")
    private String buyLink;

    public SaleInfo(String saleability, ListPrice listPrice, String buyLink) {
        this.saleability = saleability;
        this.listPrice = listPrice;
        this.buyLink = buyLink;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }
}
