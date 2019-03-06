package com.cansoft.app.model;

import android.graphics.drawable.Drawable;

public class Social {

    public int image;
    public int fullImage;
    public Drawable imageDrw;
    public Drawable fullImageDrw;
    public String name;
    public String designation;
    public String phone;
    public String emailAddress;
    public boolean expanded = false;
    public boolean parent = false;

    // flag when item swiped
    public boolean swiped = false;

    public Social() {
    }

    public Social(int image, String name,int fullImage, String designation,String phone,String emailAddress) {
        this.image = image;
        this.name = name;
        this.fullImage = fullImage;
        this.designation = designation;
        this.phone = phone;
        this.emailAddress = emailAddress;
    }
}

