package com.cansoft.cansoft.cansoft.util;

import android.content.Context;
import android.content.res.TypedArray;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.model.Social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataGenerator {

    public static List<Social> getSocialData(Context ctx) {
        List<Social> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.social_images);
        TypedArray drw_full = ctx.getResources().obtainTypedArray(R.array.full_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.social_names);
        String designation_arr[] = ctx.getResources().getStringArray(R.array.designation);
        String phone_arr[] = ctx.getResources().getStringArray(R.array.phone);
        String email_arr[] = ctx.getResources().getStringArray(R.array.email_address);

        for (int i = 0; i < drw_arr.length(); i++) {
            Social obj = new Social();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.fullImage = drw_full.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.designation = designation_arr[i];
            obj.phone = phone_arr[i];
            obj.emailAddress = email_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            obj.fullImageDrw = ctx.getResources().getDrawable(obj.fullImage);
            items.add(obj);
        }

        return items;
    }
}
