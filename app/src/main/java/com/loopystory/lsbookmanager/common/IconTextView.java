package com.loopystory.lsbookmanager.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author seogangmin
 * @version 0.0.1 2016. 5. 20. 생성
 */
public class IconTextView extends TextView{

    public static final String FONTAWESOME = "fonts/fontawesome-webfont.ttf";

    public IconTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), FONTAWESOME);
        this.setTypeface(face);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), FONTAWESOME);
        this.setTypeface(face);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), FONTAWESOME);
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);

    }
}
