package com.royan.twincongress.helpers;

import android.content.Context;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.royan.twincongress.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by szamani on 8/26/2017.
 */

public class FirstLetterDrawableHelper {
    private static Map<String, TextDrawable> bigDrawableMap;
    private static Map<String, TextDrawable> smallDrawableMap;
    private static Map<String, Integer> colorMap;
    private static String alphabets[] = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static int randomColors[];

    public static void initAllDrawables(Context context) {
        if (bigDrawableMap == null)
            bigDrawableMap = new HashMap<>();
        if (smallDrawableMap == null)
            smallDrawableMap = new HashMap<>();
        if (colorMap == null)
            colorMap = new HashMap<>();
        if (randomColors == null)
            randomColors = context.getResources().getIntArray(R.array.top_bar_colors);

        for (int i = 0; i < alphabets.length; i++) {
            int color = randomColors[RandomHelper.random.nextInt(randomColors.length)];
            bigDrawableMap.put(alphabets[i], TextDrawable
                    .builder()
                    .beginConfig()
                    .width(100)
                    .height(100)
                    .endConfig()
                    .buildRound(alphabets[i], color));
            smallDrawableMap.put(alphabets[i], TextDrawable
                    .builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRound(alphabets[i], color));
            colorMap.put(alphabets[i], color);
        }

    }

    public static TextDrawable getBigDrawable(String letter, Context context, View v) {
        if (smallDrawableMap == null ||
                bigDrawableMap == null || colorMap == null)
            initAllDrawables(context);

        if (!alphabet.contains(letter))
            letter = "X";

        if (v != null)
            v.setBackgroundColor(colorMap.get(letter));

        return bigDrawableMap.get(letter);
    }

    public static TextDrawable getSmallDrawable(String letter, Context context) {
        if (smallDrawableMap == null ||
                bigDrawableMap == null || colorMap == null)
            initAllDrawables(context);

        if (!alphabet.contains(letter))
            letter = "X";

        return smallDrawableMap.get(letter);
    }

    public static void setViewRandomColor(Context context, View v) {
        if (smallDrawableMap == null ||
                bigDrawableMap == null || colorMap == null)
            initAllDrawables(context);

        if (v != null)
            v.setBackgroundColor(randomColors
                    [RandomHelper.random.nextInt(randomColors.length)]);
    }
}
