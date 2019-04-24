package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppInfo {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project_info")
    @Expose
    private String projectInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

}
