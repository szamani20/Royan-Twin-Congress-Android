package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.royan.twincongress.R;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.picassoHelper.CircleTransform;
import com.squareup.picasso.Picasso;

/**
 * Created by szamani on 8/10/2017.
 */

public class CompanyDetailActivity extends AppCompatActivity {
    private ImageView companyCover;
    private Company sponsor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String title = getIntent().getStringExtra(Constants.COMPANY_NAME);
        if (title != null && title.length() != 0)
            toolbar.setTitle(title);
        else toolbar.setTitle(R.string.sponsor);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        companyCover = (ImageView) findViewById(R.id.companyCover);
        initSponsor();
        FontHelper.applyDefaultFont(findViewById(R.id.activityCompanyDetailLayout));
    }

    private void initSponsor() {
        int companyID = getIntent().getIntExtra(Constants.COMPANY_ID, 0);
        if (DataEntries.Sponsor_Company != null)
            for (Company c : DataEntries.Sponsor_Company)
                if (c.id == companyID) {
                    sponsor = c;
                    break;
                }

        if (sponsor == null) {
            companyCover.setImageDrawable(getResources().getDrawable(R.drawable.ic_landscape));
            return;
        }

        if (sponsor.pics != null && sponsor.pics.size() > 0
                && sponsor.pics.get(0) != null && sponsor.pics.get(0).getValue().length() != 0)
            Picasso.with(this).load(sponsor.pics.get(0).getValue())
                    .centerCrop()
                    .fit()
                    .placeholder(getResources().getDrawable(R.drawable.ic_landscape))
                    .into(companyCover);
        else
            companyCover.setImageDrawable(getResources().getDrawable(R.drawable.ic_landscape));
    }
}
