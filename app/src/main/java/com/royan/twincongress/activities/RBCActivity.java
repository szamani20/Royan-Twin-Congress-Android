package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.royan.twincongress.R;
import com.royan.twincongress.adapters.SpeakerAdapter;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Speaker;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

import static com.royan.twincongress.helpers.Constants.SPEAKER_FETCH_OFFSET;
import static com.royan.twincongress.helpers.Constants.SPEAKER_FETCH_SIZE;


/**
 * Created by szamani on 7/26/2017.
 */

public class RBCActivity extends CongressBaseActivity {
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rbc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.reproductive_biomedicine_congress);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        congressType = 1;
        initDefaultTabModels();
        initRecyclerView();
        initModels();
        initViews();

        toolbar.inflateMenu(R.menu.menu_search);
        setupTapTarget();
        FontHelper.applyDefaultFont(findViewById(R.id.activity_rbc));
    }

    @Override
    protected void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.speakerRView);
        adapter = new SpeakerAdapter(this, DataEntries.RBC_IS_Speaker,
                DataType.InvitedSpeaker);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void setGeneralContent(DataType dataType) {
        if (adapter.dataType == dataType && adapter.speakers != null &&
                adapter.speakers.size() != 0)
            return;

        switch (dataType) {
            case InvitedSpeaker:
                if (DataEntries.RBC_IS_Speaker.size() >= Constants.SPEAKER_FETCH_SIZE) {
                    DataEntries.RBC_IS_Speaker = DataEntries.RBC_IS_Speaker.subList(0, Constants.SPEAKER_FETCH_SIZE);
                    Constants.SPEAKER_FETCH_OFFSET[Constants.RBC_CONGRESS][Constants.IS_SPEAKER] = Constants.SPEAKER_FETCH_SIZE;
                }
                adapter.speakers = DataEntries.RBC_IS_Speaker;
                break;
            case OralPresentation:
                if (DataEntries.RBC_OP_Speaker.size() >= Constants.SPEAKER_FETCH_SIZE) {
                    DataEntries.RBC_OP_Speaker = DataEntries.RBC_OP_Speaker.subList(0, Constants.SPEAKER_FETCH_SIZE);
                    Constants.SPEAKER_FETCH_OFFSET[Constants.RBC_CONGRESS][Constants.OP_SPEAKER] = Constants.SPEAKER_FETCH_SIZE;
                }
                adapter.speakers = DataEntries.RBC_OP_Speaker;
                break;
            case Poster:
                if (DataEntries.RBC_Poster_Speaker.size() >= Constants.SPEAKER_FETCH_SIZE) {
                    DataEntries.RBC_Poster_Speaker = DataEntries.RBC_Poster_Speaker.subList(0, Constants.SPEAKER_FETCH_SIZE);
                    Constants.SPEAKER_FETCH_OFFSET[Constants.RBC_CONGRESS][Constants.POSTER_SPEAKER] = Constants.SPEAKER_FETCH_SIZE;
                }
                adapter.speakers = DataEntries.RBC_Poster_Speaker;
                break;
        }

        adapter.dataType = dataType;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void populateIS() {
//        System.out.println("RBC Just before Populate IS");
        if (DataEntries.RBC_IS_Speaker == null ||
                DataEntries.RBC_IS_Speaker.size() == 0) {
//            System.out.println("RBC Populate IS");
            DataEntries.RBC_IS_Speaker = new ArrayList<>();
            fetchData(Constants.RBC_CONGRESS, Constants.IS_SPEAKER);
        }
    }

    @Override
    protected void populateOP() {
        if (DataEntries.RBC_OP_Speaker == null ||
                DataEntries.RBC_OP_Speaker.size() == 0) {
            DataEntries.RBC_OP_Speaker = new ArrayList<>();
            fetchData(Constants.RBC_CONGRESS, Constants.OP_SPEAKER);
        }
    }

    @Override
    protected void populateP() {
        if (DataEntries.RBC_Poster_Speaker == null ||
                DataEntries.RBC_Poster_Speaker.size() == 0) {
            DataEntries.RBC_Poster_Speaker = new ArrayList<>();
            fetchData(Constants.RBC_CONGRESS, Constants.POSTER_SPEAKER);
        }
    }

    @Override
    protected void setSearchContent() {
        if (DataEntries.RBC_Search_Result != null &&
                DataEntries.RBC_Search_Result.size() != 0) {
            adapter.speakers = DataEntries.RBC_Search_Result;
            adapter.dataType = DataType.Search;
            adapter.notifyDataSetChanged();
        }

        showSearchDialog();
    }

    @Override
    protected void fetchData(int congress, int type) {
        if (realm == null)
            realm = Realm.getDefaultInstance();
        OrderedRealmCollection<Speaker> speakers =
                realm.where(Speaker.class)
                        .equalTo("congress", congress)
                        .equalTo("type", type)
                        .between("id", SPEAKER_FETCH_OFFSET[congress][type], SPEAKER_FETCH_OFFSET[congress][type] + SPEAKER_FETCH_SIZE)
                        .findAllSorted("sortCriteria");

        // No more data for this tab
        if (speakers == null ||
                speakers.size() == 0)
            return;

        SPEAKER_FETCH_OFFSET[congress][type] += speakers.size();

//        System.out.println("FETCH " + speakers.size());

        switch (type) {
            case Constants.IS_SPEAKER:
                // Load more in this tab
                if (adapter != null && adapter.dataType == DataType.InvitedSpeaker &&
                        adapter.speakers != null && adapter.speakers.size() != 0) {
//                    System.out.println("BEFORE: " + adapter.speakers.size());
                    for (Speaker s : speakers)
                        adapter.speakers.add(s);
//                    System.out.println("AFTER: " + adapter.speakers.size());
                    DataEntries.RBC_IS_Speaker = adapter.speakers;

                    adapter.notifyDataSetChanged();
                }

                // new tab is selected
                else {
                    // the first time tab data is being created
                    if (DataEntries.RBC_IS_Speaker == null)
                        DataEntries.RBC_IS_Speaker = new ArrayList<>();
                    for (Speaker s : speakers)
                        DataEntries.RBC_IS_Speaker.add(s);
                }
                break;
            case Constants.OP_SPEAKER:
                if (adapter != null && adapter.dataType == DataType.OralPresentation &&
                        adapter.speakers != null && adapter.speakers.size() != 0) {
//                    System.out.println("BEFORE: " + adapter.speakers.size());
                    for (Speaker s : speakers)
                        adapter.speakers.add(s);
//                    System.out.println("AFTER: " + adapter.speakers.size());
                    DataEntries.RBC_OP_Speaker = adapter.speakers;

                    adapter.notifyDataSetChanged();
                } else {
                    if (DataEntries.RBC_OP_Speaker == null)
                        DataEntries.RBC_OP_Speaker = new ArrayList<>();
                    for (Speaker s : speakers)
                        DataEntries.RBC_OP_Speaker.add(s);
                }
                break;
            case Constants.POSTER_SPEAKER:
                if (adapter != null && adapter.dataType == DataType.Poster &&
                        adapter.speakers != null && adapter.speakers.size() != 0) {
//                    System.out.println("BEFORE: " + adapter.speakers.size());
                    for (Speaker s : speakers)
                        adapter.speakers.add(s);
//                    System.out.println("AFTER: " + adapter.speakers.size());
                    DataEntries.RBC_Poster_Speaker = adapter.speakers;

                    adapter.notifyDataSetChanged();
                } else {
                    if (DataEntries.RBC_Poster_Speaker == null)
                        DataEntries.RBC_Poster_Speaker = new ArrayList<>();
                    for (Speaker s : speakers)
                        DataEntries.RBC_Poster_Speaker.add(s);
                }
                break;
        }

    }

    @Override
    protected void performSearch() {
        if (searchCriteria == null ||
                searchCriteria.length() < 2 ||
                searchCriteria.length() > 30)
            return;

        if (realm == null)
            realm = Realm.getDefaultInstance();

        DataEntries.RBC_Search_Result =
                realm.where(Speaker.class)
                        .equalTo("congress", 0)
                        .like("name", "adsfasd", Case.INSENSITIVE)
//                        .or()
//                        .like("country", searchCriteria, Case.INSENSITIVE)
//                        .or()
//                        .like("affiliation", searchCriteria, Case.INSENSITIVE)
//                        .or()
//                        .like("topic", searchCriteria, Case.INSENSITIVE)
//                        .or()
//                        .like("aabstract.keyword", searchCriteria, Case.INSENSITIVE)
                        .findAllSorted("type");

//        System.out.println("SEARCH: " + DataEntries.RBC_Search_Result.size());

        adapter.speakers = DataEntries.RBC_Search_Result;
        adapter.dataType = DataType.Search;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }

}
