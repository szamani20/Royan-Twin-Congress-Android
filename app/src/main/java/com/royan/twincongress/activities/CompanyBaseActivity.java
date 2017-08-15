package com.royan.twincongress.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.royan.twincongress.adapters.CompanyAdapter;

/**
 * Created by szamani on 7/30/2017.
 */

public abstract class CompanyBaseActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    protected CompanyAdapter adapter;

    protected abstract void initRecyclerView();

    protected abstract void initModel();

    protected abstract void fetchData();
}
