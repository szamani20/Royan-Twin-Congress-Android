package com.royan.twincongress.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by szamani on 8/5/2017.
 */

public class SnackBarHelper {
    public static void showSnackbar(Context context, View view, String message, int duration) {
        System.out.println("HELLO1");
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        System.out.println("HELLO2");
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        System.out.println("HELLO3");
        Snackbar.make(view, message, duration).show();
        System.out.println("HELLO4");
    }
}
