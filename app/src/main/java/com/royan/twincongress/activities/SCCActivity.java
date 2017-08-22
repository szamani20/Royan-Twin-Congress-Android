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

public class SCCActivity extends CongressBaseActivity {
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.stem_cell_congress);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        congressType = 0;
        initDefaultTabModels();
        initRecyclerView();
        initModels();
        initViews();

        toolbar.inflateMenu(R.menu.menu_search);
        setupTapTarget();
        FontHelper.applyDefaultFont(findViewById(R.id.activity_scc));
    }

    @Override
    protected void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.speakerRView);
        adapter = new SpeakerAdapter(this, DataEntries.SCC_IS_Speaker,
                DataType.InvitedSpeaker);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void setGeneralContent(DataType dataType) {
        // Tab reselect --> Same tab
        if (adapter.dataType == dataType && adapter.speakers != null &&
                adapter.speakers.size() != 0) {
            return;
        }
        // Changed tab
        switch (dataType) {
            case InvitedSpeaker:
                if (DataEntries.SCC_IS_Speaker.size() >= Constants.SPEAKER_FETCH_SIZE) {
                    System.out.println("General Content 3");
                    DataEntries.SCC_IS_Speaker = DataEntries.SCC_IS_Speaker.subList(0, Constants.SPEAKER_FETCH_SIZE);
                    Constants.SPEAKER_FETCH_OFFSET[Constants.SCC_CONGRESS][Constants.IS_SPEAKER] = Constants.SPEAKER_FETCH_SIZE;
                }
                adapter.speakers = DataEntries.SCC_IS_Speaker;
                break;
            case OralPresentation:
                if (DataEntries.SCC_OP_Speaker.size() >= Constants.SPEAKER_FETCH_SIZE) {
                    DataEntries.SCC_OP_Speaker = DataEntries.SCC_OP_Speaker.subList(0, Constants.SPEAKER_FETCH_SIZE);
                    Constants.SPEAKER_FETCH_OFFSET[Constants.SCC_CONGRESS][Constants.OP_SPEAKER] = Constants.SPEAKER_FETCH_SIZE;
                }
                adapter.speakers = DataEntries.SCC_OP_Speaker;
                break;
            case Poster:
                if (DataEntries.SCC_Poster_Speaker.size() >= Constants.SPEAKER_FETCH_SIZE) {
                    DataEntries.SCC_Poster_Speaker = DataEntries.SCC_Poster_Speaker.subList(0, Constants.SPEAKER_FETCH_SIZE);
                    Constants.SPEAKER_FETCH_OFFSET[Constants.SCC_CONGRESS][Constants.POSTER_SPEAKER] = Constants.SPEAKER_FETCH_SIZE;
                }
                adapter.speakers = DataEntries.SCC_Poster_Speaker;
                break;
        }

        // Change adapter dataType for selected tab

        adapter.dataType = dataType;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void populateIS() {  // could be abstract
        System.out.println(DataEntries.SCC_IS_Speaker == null);
        if (DataEntries.SCC_IS_Speaker != null)
            System.out.println(DataEntries.SCC_IS_Speaker.size());
        if (DataEntries.SCC_IS_Speaker == null ||
                DataEntries.SCC_IS_Speaker.size() == 0) {
            System.out.println("YOU ARE GODDAMN RIGHT");
            DataEntries.SCC_IS_Speaker = new ArrayList<>();
            fetchData(Constants.SCC_CONGRESS, Constants.IS_SPEAKER);
        }
    }

    @Override
    protected void populateOP() {
        if (DataEntries.SCC_OP_Speaker == null ||
                DataEntries.SCC_OP_Speaker.size() == 0) {
            DataEntries.SCC_OP_Speaker = new ArrayList<>();
            fetchData(Constants.SCC_CONGRESS, Constants.OP_SPEAKER);
        }
    }

    @Override
    protected void populateP() {
        if (DataEntries.SCC_Poster_Speaker == null ||
                DataEntries.SCC_Poster_Speaker.size() == 0) {
            DataEntries.SCC_Poster_Speaker = new ArrayList<>();
            fetchData(Constants.SCC_CONGRESS, Constants.POSTER_SPEAKER);
        }
    }

    @Override
    protected void fetchData(int congress, int type) {
        if (realm == null)
            realm = Realm.getDefaultInstance();

        // only on initializing and load more
        OrderedRealmCollection<Speaker> speakers =
                realm.where(Speaker.class)
                        .equalTo("congress", congress)
                        .equalTo("type", type)
                        .between("id", SPEAKER_FETCH_OFFSET[congress][type], SPEAKER_FETCH_OFFSET[congress][type] + SPEAKER_FETCH_SIZE)
                        .findAllSorted("id");

        // No more data for this tab
        if (speakers == null ||
                speakers.size() == 0)
            return;

        SPEAKER_FETCH_OFFSET[congress][type] += speakers.size();

        System.out.println("FETCH " + speakers.size());

        switch (type) {
            case Constants.IS_SPEAKER:
                // Load more in this tab
                if (adapter != null && adapter.dataType == DataType.InvitedSpeaker &&
                        adapter.speakers != null && adapter.speakers.size() != 0) {
                    System.out.println("BEFORE: " + adapter.speakers.size());
                    for (Speaker s : speakers)
                        adapter.speakers.add(s);
                    System.out.println("AFTER: " + adapter.speakers.size());
                    DataEntries.SCC_IS_Speaker = adapter.speakers;

                    adapter.notifyDataSetChanged();
                }

                // new tab is selected
                else {
                    // the first time tab data is being created
                    if (DataEntries.SCC_IS_Speaker == null)
                        DataEntries.SCC_IS_Speaker = new ArrayList<>();
                    for (Speaker s : speakers)
                        DataEntries.SCC_IS_Speaker.add(s);
                }
                break;
            case Constants.OP_SPEAKER:
                if (adapter != null && adapter.dataType == DataType.OralPresentation &&
                        adapter.speakers != null && adapter.speakers.size() != 0) {
                    System.out.println("BEFORE: " + adapter.speakers.size());
                    for (Speaker s : speakers)
                        adapter.speakers.add(s);
                    System.out.println("AFTER: " + adapter.speakers.size());
                    DataEntries.SCC_OP_Speaker = adapter.speakers;

                    adapter.notifyDataSetChanged();
                } else {
                    if (DataEntries.SCC_OP_Speaker == null)
                        DataEntries.SCC_OP_Speaker = new ArrayList<>();
                    for (Speaker s : speakers)
                        DataEntries.SCC_OP_Speaker.add(s);
                }
                break;
            case Constants.POSTER_SPEAKER:
                if (adapter != null && adapter.dataType == DataType.Poster &&
                        adapter.speakers != null && adapter.speakers.size() != 0) {
                    System.out.println("BEFORE: " + adapter.speakers.size());
                    for (Speaker s : speakers)
                        adapter.speakers.add(s);
                    System.out.println("AFTER: " + adapter.speakers.size());
                    DataEntries.SCC_Poster_Speaker = adapter.speakers;

                    adapter.notifyDataSetChanged();
                } else {
                    if (DataEntries.SCC_Poster_Speaker == null)
                        DataEntries.SCC_Poster_Speaker = new ArrayList<>();
                    for (Speaker s : speakers)
                        DataEntries.SCC_Poster_Speaker.add(s);
                }
                break;
        }
    }

    @Override
    protected void setSearchContent() {
        if (DataEntries.SCC_Search_Result != null &&
                DataEntries.SCC_Search_Result.size() != 0) {
            for (Speaker s : DataEntries.SCC_Search_Result)
                System.out.println(s.name);
            adapter.speakers = DataEntries.SCC_Search_Result;
            adapter.dataType = DataType.Search;
            adapter.notifyDataSetChanged();
        }

        showSearchDialog();
    }

    @Override
    protected void performSearch() {  // Called on search button clicked
        if (searchCriteria == null ||
                searchCriteria.length() < 2 ||
                searchCriteria.length() > 30)
            return;

        if (realm == null)
            realm = Realm.getDefaultInstance();

        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHH" + searchCriteria);

        DataEntries.SCC_Search_Result =
                realm.where(Speaker.class)
                        .equalTo("congress", 0)
                        .like("name", "sadas", Case.INSENSITIVE)
//                        .or()
//                        .like("name", "*"+searchCriteria+"?", Case.INSENSITIVE)
//                        .or()
//                        .like("affiliation", searchCriteria, Case.INSENSITIVE)
//                        .or()
//                        .like("country", searchCriteria, Case.INSENSITIVE)
//                        .or()
//                        .like("topic", searchCriteria, Case.INSENSITIVE)
//                        .or()
//                        .like("aabstract.keyword", searchCriteria, Case.INSENSITIVE)
                        .findAllSorted("type");

        System.out.println("SEARCH: " + DataEntries.SCC_Search_Result.size());

        adapter.speakers = DataEntries.SCC_Search_Result;
        adapter.dataType = DataType.Search;
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        realm.close();
    }

}
