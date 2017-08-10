package com.royan.twincongress.activities;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.royan.twincongress.R;
import com.royan.twincongress.adapters.CompanyAdapter;
import com.royan.twincongress.models.Company;

import java.util.List;

/**
 * Created by szamani on 7/30/2017.
 */

public abstract class CompanyBaseActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    protected CompanyAdapter adapter;
    protected LayoutInflater inflater;
    protected NestedScrollView nested;

    protected abstract void initRecyclerView();

    protected abstract void initModel();

    protected abstract void fetchData();

    protected void initViews() {
        inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nested = (NestedScrollView) findViewById(R.id.nested);
    }
}
