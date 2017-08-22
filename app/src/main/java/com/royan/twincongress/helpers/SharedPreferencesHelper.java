package com.royan.twincongress.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by szamani on 7/25/2017.
 */

public class SharedPreferencesHelper {
    private static final String VERIFICATION_KEY = "VERIFICATION_KEY";
    private static final String DATA_DOWNLOADED_KEY = "DATA_DOWNLOAD";
    private static final String PREF_NAME = "SharedPreferencesHelper";

    public static boolean getVerified(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, 0);

        if (!preferences.contains(VERIFICATION_KEY)) { // First time opening app
            Editor editor = preferences.edit();
            editor.putBoolean(VERIFICATION_KEY, false);
            editor.apply();

            return false;
        }

        return preferences.getBoolean(VERIFICATION_KEY, true);
    }

    // only called once
    public static void setVerified(Context context, boolean verified) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, 0);

        Editor editor = preferences.edit();
        editor.putBoolean(VERIFICATION_KEY, verified);
        editor.apply();
    }

    public static boolean getActivityTapTarget(Context context, final String activityName) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, 0);

        if (!preferences.contains(activityName)) {
            Editor editor = preferences.edit();
            editor.putBoolean(activityName, true);
            editor.apply();

            return false;
        }

        return preferences.getBoolean(activityName, true);
    }

    public static boolean getDataDownloaded(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, 0);

        if (!preferences.contains(DATA_DOWNLOADED_KEY)) { // First time opening app
            Editor editor = preferences.edit();
            editor.putBoolean(DATA_DOWNLOADED_KEY, false);
            editor.apply();

            return false;
        }

        return preferences.getBoolean(DATA_DOWNLOADED_KEY, false);
    }

    public static void setDataDownloaded(Context context, boolean dataDownloaded) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, 0);

        Editor editor = preferences.edit();
        editor.putBoolean(DATA_DOWNLOADED_KEY, dataDownloaded);
        editor.apply();
    }
}
