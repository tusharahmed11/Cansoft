package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoD {
    @SerializedName("data")
    @Expose
    private List<AppInfo> data = null;

    public List<AppInfo> getData() {
        return data;
    }

    public void setData(List<AppInfo> data) {
        this.data = data;
    }
}
