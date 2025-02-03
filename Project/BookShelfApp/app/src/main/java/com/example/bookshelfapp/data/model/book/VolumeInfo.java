package com.example.bookshelfapp.data.model.book;

import com.google.gson.annotations.SerializedName;

public class VolumeInfo {
    @SerializedName("title")
    protected String title;
    @SerializedName("imageLinks")
    protected ImageLink imageLinks;

    protected double rating;
    protected int view;

    public VolumeInfo(String title, ImageLink imageLinks) {
        this.title = title;
        this.imageLinks = imageLinks;
        this.rating=0;
        this.view=0;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageLink getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLink imageLinks) {
        this.imageLinks = imageLinks;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
