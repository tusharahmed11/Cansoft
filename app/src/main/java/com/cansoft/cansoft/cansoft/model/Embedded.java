package com.cansoft.cansoft.cansoft.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {

    @SerializedName("wp:featuredmedia")
    @Expose
    private List<WpFeaturedmedium_> wpFeaturedmedia = null;




    public List<WpFeaturedmedium_> getWpFeaturedmedia() {
        return wpFeaturedmedia;
    }

    public void setWpFeaturedmedia(List<WpFeaturedmedium_> wpFeaturedmedia) {
        this.wpFeaturedmedia = wpFeaturedmedia;
    }

}
