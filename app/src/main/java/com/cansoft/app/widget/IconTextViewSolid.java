package com.cansoft.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

public class IconTextViewSolid extends android.support.v7.widget.AppCompatTextView {

    private Context context;

    public IconTextViewSolid(Context context) {
        super(context);
        this.context = context;
        createView();
    }

    public IconTextViewSolid(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    private void createView(){
        setGravity(Gravity.CENTER);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonticon/FontAwesomeSolid.otf");
        setTypeface(font);
    }
}
