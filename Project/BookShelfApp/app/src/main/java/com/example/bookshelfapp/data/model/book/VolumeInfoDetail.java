package com.example.bookshelfapp.data.model.book;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeInfoDetail extends VolumeInfo {
    @SerializedName("authors")
    private List<String> authors;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("publishedDate")
    private String publishedDate;
    @SerializedName("pageCount")
    private int pageCount;
    @SerializedName("categories")
    private List<String> categories;
    @SerializedName("language")
    private String language;
    @SerializedName("saleInfo")
    private SaleInfo saleInfo;
    @SerializedName("accessInfo")
    private AccessInfo accessInfo;

    public VolumeInfoDetail(String title, ImageLink imageLinks,
                            List<String> authors, String publisher,
                            String publishedDate, int pageCount,
                            List<String> categories, String language,
                            SaleInfo saleInfo, AccessInfo accessInfo) {
        super(title, imageLinks);
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.categories = categories;
        this.language = language;
        this.saleInfo = saleInfo;
        this.accessInfo = accessInfo;

    }

    public VolumeInfoDetail(String title, ImageLink imageLinks) {
        super(title, imageLinks);
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }
}
