package com.cansoft.app.model;

import com.cansoft.app.model.Client;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientD {
    @SerializedName("data")
    @Expose
    private List<Client> data = null;

    public List<Client> getData() {
        return data;
    }

    public void setData(List<Client> data) {
        this.data = data;
    }
}
