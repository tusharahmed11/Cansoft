package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Excerpt {
    @SerializedName("rendered")
    @Expose
    private String rendered;
    @SerializedName("protected")
    @Expose
    private Boolean _protected;

    public Excerpt(String rendered, Boolean _protected) {
        this.rendered = rendered;
        this._protected = _protected;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public Boolean get_protected() {
        return _protected;
    }

    public void set_protected(Boolean _protected) {
        this._protected = _protected;
    }
}
