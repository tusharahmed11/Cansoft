package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WpFeaturedmedium_ {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("source_url")
    @Expose
    private String sourceUrl;

    public WpFeaturedmedium_(Integer id, String sourceUrl) {
        this.id = id;
        this.sourceUrl = sourceUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
