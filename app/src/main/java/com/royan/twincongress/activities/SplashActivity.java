package com.royan.twincongress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.royan.twincongress.helpers.SharedPreferencesHelper;

/**
 * Created by szamani on 7/25/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "SPLASH_ACTIVITY_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesHelper.getVerified(getApplicationContext()))
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        else startActivity(new Intent(SplashActivity.this, VerificationActivity.class));
        finish();
    }
}