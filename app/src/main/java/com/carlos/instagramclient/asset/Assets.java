package com.carlos.instagramclient.asset;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Assets {

    public static final String INSTAGRAM_TITLE_FONT = "fonts/billabong.ttf";

    public static void setInstagramTitleFont(TextView textView, Context context){
        Typeface font = Typeface.createFromAsset(context.getAssets(), INSTAGRAM_TITLE_FONT);
        textView.setTypeface(font);
    }
}
