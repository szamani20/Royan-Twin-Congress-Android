package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.royan.twincongress.R;
import com.royan.twincongress.adapters.CompanyAdapter;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.DataType;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

import static com.royan.twincongress.helpers.Constants.COMPANY_FETCH_OFFSET;
import static com.royan.twincongress.helpers.Constants.COMPANY_FETCH_SIZE;
import static com.royan.twincongress.helpers.Constants.COMPANY_SPONSOR;

/**
 * Created by szamani on 7/30/2017.
 */

public class SponsorActivity extends CompanyBaseActivity {
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.sponsors);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initModel();
        initRecyclerView();
    }

    @Override
    protected void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.sponsorRView);
        adapter = new CompanyAdapter(this,
                DataEntries.Sponsor_Company, DataType.SponsorCompany);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initModel() {  // could be abstract
        if (DataEntries.Sponsor_Company == null ||
                DataEntries.Sponsor_Company.size() == 0) {
            DataEntries.Sponsor_Company = new ArrayList<>();
            fetchData();
        }

        for (Company c : DataEntries.Sponsor_Company)
            System.out.println(c.id + " " + c.website);
    }

    @Override
    protected void fetchData() {  // could be abstract
        if (realm == null)
            realm = Realm.getDefaultInstance();

        OrderedRealmCollection<Company> companies =
                realm.where(Company.class)
                        .equalTo("type", COMPANY_SPONSOR)
                        .between("id", COMPANY_FETCH_OFFSET[COMPANY_SPONSOR], COMPANY_FETCH_OFFSET[COMPANY_SPONSOR] + COMPANY_FETCH_SIZE)
                        .findAllSorted("id");

        if (companies == null ||
                companies.size() == 0)
            return;

        COMPANY_FETCH_OFFSET[COMPANY_SPONSOR] += companies.size();

        System.out.println("FETCH " + companies.size());

        // Load more in this screen
        if (adapter != null && adapter.dataType == DataType.SponsorCompany &&
                adapter.companies != null && adapter.companies.size() != 0) {
            for (Company c : companies)
                adapter.companies.add(c);
            DataEntries.Sponsor_Company = adapter.companies;

            adapter.notifyDataSetChanged();
        }

        // first time data is being created
        else {
            if (DataEntries.Sponsor_Company == null)
                DataEntries.Sponsor_Company = new ArrayList<>();
            for (Company c : companies)
                DataEntries.Sponsor_Company.add(c);
        }
    }
}
