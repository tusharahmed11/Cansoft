package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageD {

    @SerializedName("data")
    @Expose
    private Image data;

    public Image getData() {
        return data;
    }

    public void setData(Image data) {
        this.data = data;
    }
}
