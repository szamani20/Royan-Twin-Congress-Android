package com.royan.twincongress.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by szamani on 8/21/2017.
 */

public class FontHelper {
    static Typeface font;
    static Typeface fontBlack;
    static Typeface fontSub;

    public static void applyDefaultFont(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++)
                applyDefaultFont(viewGroup.getChildAt(i));
        } else {
            if (v instanceof TextView) {
                TextView textView = (TextView) v;
                if (textView.getTypeface() != null && textView.getTypeface().getStyle() == Typeface.BOLD)
                    textView.setTypeface(fontBlack);
                else textView.setTypeface(font);
            }
        }

    }

    public static Typeface getFont(Context context) {
        if (font == null)
            font = Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd-Light.otf");
        if (fontBlack == null)
            fontBlack = Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd-Black.otf");
        return font;
    }
}
