package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoD {
    @SerializedName("data")
    @Expose
    private Photo data;

    public Photo getData() {
        return data;
    }

    public void setData(Photo data) {
        this.data = data;
    }
}
