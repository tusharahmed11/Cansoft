package com.cansoft.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

public class IconTextView2 extends android.support.v7.widget.AppCompatTextView {

    private Context context;

    public IconTextView2(Context context) {
        super(context);
        this.context = context;
        createView();
    }

    public IconTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.CENTER);
        Typeface font1 = Typeface.createFromAsset(context.getAssets(), "fonticon/FontAwesomeBrands.otf");
        setTypeface(font1);
    }
}
