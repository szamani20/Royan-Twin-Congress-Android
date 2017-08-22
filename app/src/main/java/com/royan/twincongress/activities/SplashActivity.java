package com.royan.twincongress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.royan.twincongress.helpers.SharedPreferencesHelper;

/**
 * Created by szamani on 7/25/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesHelper.getVerified(getApplicationContext()) &&
                SharedPreferencesHelper.getDataDownloaded(getApplicationContext()))  // verified and already downloaded
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

        if (SharedPreferencesHelper.getVerified(getApplicationContext()) &&
                !SharedPreferencesHelper.getDataDownloaded(getApplicationContext()))  // verified but not yet downloaded
            startActivity(new Intent(SplashActivity.this, FirstDownloadActivity.class));

        if (!SharedPreferencesHelper.getVerified(getApplicationContext()))  // not yet verified
            startActivity(new Intent(SplashActivity.this, VerificationActivity.class));

        finish();
    }
}