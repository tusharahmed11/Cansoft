package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutD {
    @SerializedName("data")
    @Expose
    private List<About> data = null;

    public List<About> getData() {
        return data;
    }

    public void setData(List<About> data) {
        this.data = data;
    }
}
