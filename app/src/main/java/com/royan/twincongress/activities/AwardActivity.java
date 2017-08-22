package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.royan.twincongress.R;
import com.royan.twincongress.adapters.WinnerAdapter;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Winner;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;


/**
 * Created by szamani on 7/26/2017.
 */

public class AwardActivity extends AppCompatActivity {
    private WinnerAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.awards);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initDefaultTabModels();
        initRecyclerView();
        initModels();
        initViews();

        FontHelper.applyDefaultFont(findViewById(R.id.akp_activity));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.winnerRView);
        adapter = new WinnerAdapter(this, DataEntries.AKP_International_Winner,
                DataType.NationalWinner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initDefaultTabModels() {
        populateInternational();
    }

    private void initViews() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {   // into OOD? --> initialize tabs in app startup and use the list here
                    case R.id.tab_in:
                        setTabContent(DataType.InternationalWinner);
                        break;
                    case R.id.tab_n:
                        setTabContent(DataType.NationalWinner);
                        break;
                }
            }
        });
        bottomBar.setSelected(false);
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                // TODO: cache contents maybe
            }
        });
    }

    private void setTabContent(DataType dataType) {
        if (adapter.dataType == dataType &&
                adapter.winners != null &&
                adapter.winners.size() != 0)
            return;

        switch (dataType) {
            case InternationalWinner:
                adapter.winners = DataEntries.AKP_International_Winner;
                break;
            case NationalWinner:
                adapter.winners = DataEntries.AKP_National_Winner;
                break;
        }

        adapter.dataType = dataType;
        adapter.notifyDataSetChanged();
    }

    private void initModels() {
        populateNational();
    }

    private void populateInternational() {
        if (DataEntries.AKP_International_Winner == null ||
                DataEntries.AKP_National_Winner.size() == 0)
            fetchData(Constants.WINNER_INTERNATIONAL);
    }

    private void populateNational() {
        if (DataEntries.AKP_National_Winner == null ||
                DataEntries.AKP_National_Winner.size() == 0)
            fetchData(Constants.WINNER_NATIONAL);
    }

    private void fetchData(int type) {
        if (realm == null)
            realm = Realm.getDefaultInstance();

        // cause we have few winners, no need to specify range in query
        OrderedRealmCollection<Winner> winners =
                realm.where(Winner.class)
                        .equalTo("type", type)
                        .findAll();

        if (winners == null ||
                winners.size() == 0)
            return;

        switch (type) {
            case Constants.WINNER_INTERNATIONAL:
                if (DataEntries.AKP_International_Winner == null)
                    DataEntries.AKP_International_Winner = new ArrayList<>();
                for (Winner w : winners)
                    DataEntries.AKP_International_Winner.add(w);
                break;
            case Constants.WINNER_NATIONAL:
                if (DataEntries.AKP_National_Winner == null)
                    DataEntries.AKP_National_Winner = new ArrayList<>();
                for (Winner w : winners)
                    DataEntries.AKP_National_Winner.add(w);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        realm.close();
    }
}
