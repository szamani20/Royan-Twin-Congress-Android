package com.royan.twincongress.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.royan.twincongress.R;
import com.royan.twincongress.connections.FetchCongress;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.SharedPreferencesHelper;


/**
 * Created by szamani on 7/25/2017.
 */

public class VerificationActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "VERIFICATION_ACTIVITY_PREFS";
    private BootstrapEditText verificationEditText;
    private BootstrapButton verifyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        initViews();
        setViewActions();
    }

    private void initViews() {
        verificationEditText = (BootstrapEditText) findViewById(R.id.verificationEditText);
        verifyButton = (BootstrapButton) findViewById(R.id.verifyButton);
    }

    private void setViewActions() {
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = verificationEditText.getText().toString().trim();

                if (SharedPreferencesHelper.getDataDownloaded(getApplicationContext()))
                    startActivity(new Intent(VerificationActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(VerificationActivity.this, FirstDownloadActivity.class));
            }
        });
    }

    public void showSnackbar(View view, String message, int duration) {
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar.make(view, message, duration).show();
    }
}