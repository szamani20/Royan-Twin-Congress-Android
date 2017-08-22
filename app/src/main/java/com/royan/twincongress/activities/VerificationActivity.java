package com.royan.twincongress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.royan.twincongress.R;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.SharedPreferencesHelper;
import com.royan.twincongress.helpers.SnackBarHelper;

import java.util.Arrays;

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


    // if we are here, it means that the user is not verified yet.
    @OnClick(R.id.verifyButton)
    public void verifyAction(View v) {
        String verificationCode = verificationEditText.getText().toString().trim();
        if (Arrays.asList(Constants.VERIFICATION_CODES).contains(verificationCode)) {  // verification done
            SharedPreferencesHelper.setVerified(this, true);
            startActivity(new Intent(VerificationActivity.this, FirstDownloadActivity.class));
        } else
            SnackBarHelper.showSnackbar(this, v, getResources().getString(R.string.verification_code_wrong), Snackbar.LENGTH_SHORT);
    }
}
