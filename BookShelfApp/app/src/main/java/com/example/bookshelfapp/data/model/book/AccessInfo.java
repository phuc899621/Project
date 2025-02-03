package com.example.bookshelfapp.data.model.book;

import com.google.gson.annotations.SerializedName;

public class AccessInfo {
    @SerializedName("epub")
    private Epub epub;
    @SerializedName("PDF")
    private PDF pdf;

    public AccessInfo(Epub epub, PDF pdf) {
        this.epub = epub;
        this.pdf = pdf;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }
    public class Epub {
        @SerializedName("isAvailable")
        private boolean isAvailable;
        @SerializedName("downloadLink")
        private String downloadLink;
        @SerializedName("acsTokenLink")
        private String acsTokenLink;

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public String getAcsTokenLink() {
            return acsTokenLink;
        }

        public void setAcsTokenLink(String acsTokenLink) {
            this.acsTokenLink = acsTokenLink;
        }

        public Epub(boolean isAvailable, String downloadLink, String acsTokenLink) {
            this.isAvailable = isAvailable;
            this.downloadLink = downloadLink;
            this.acsTokenLink = acsTokenLink;
        }
    }
    public class PDF{
        @SerializedName("isAvailable")
        private boolean isAvailable;
        @SerializedName("downloadLink")
        private String downloadLink;
        @SerializedName("acsTokenLink")
        private String acsTokenLink;

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public String getAcsTokenLink() {
            return acsTokenLink;
        }

        public void setAcsTokenLink(String acsTokenLink) {
            this.acsTokenLink = acsTokenLink;
        }

        public PDF(boolean isAvailable, String downloadLink, String acsTokenLink) {
            this.isAvailable = isAvailable;
            this.downloadLink = downloadLink;
            this.acsTokenLink = acsTokenLink;
        }
    }
}
