package com.example.bookshelfapp.data.model.book;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey
    @SerializedName("id")
    private String id;
    @SerializedName("selfLink")
    private String selfLink;
    @SerializedName("volumeInfo")
    private VolumeInfoDetail volumeInfoDetail;

    public Book(String id, String selfLink, VolumeInfoDetail volumeInfo) {
        this.id = id;
        this.selfLink = selfLink;
        this.volumeInfoDetail = volumeInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfoDetail;
    }

    public void setVolumeInfo(VolumeInfoDetail volumeInfoDetail) {
        this.volumeInfoDetail = volumeInfoDetail;
    }
}
