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
import com.royan.twincongress.helpers.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by szamani on 7/25/2017.
 */

public class VerificationActivity extends AppCompatActivity {
    @BindView(R.id.verificationEditText)
    BootstrapEditText verificationEditText;
    @BindView(R.id.verifyButton)
    BootstrapButton verifyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.verifyButton)
    public void verifyAction() {
        String verificationCode = verificationEditText.getText().toString().trim();

        if (SharedPreferencesHelper.getDataDownloaded(getApplicationContext()))
            startActivity(new Intent(VerificationActivity.this, MainActivity.class));
        else
            startActivity(new Intent(VerificationActivity.this, FirstDownloadActivity.class));
    }
}
