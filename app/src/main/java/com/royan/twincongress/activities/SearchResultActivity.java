package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.royan.twincongress.R;
import com.royan.twincongress.adapters.SpeakerAdapter;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Speaker;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by szamani on 8/9/2017.
 */

public class SearchResultActivity extends AppCompatActivity {
    private String searchCriteria;
    private int congressType;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.search);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        congressType = getIntent().getIntExtra(Constants.CONGRESS_TYPE, 0);
        searchCriteria = getIntent().getStringExtra(Constants.SEARCH_CRITERIA);
        if (searchCriteria == null)
            searchCriteria = "NOTHING";

        initSearchResults();
        FontHelper.applyDefaultFont(findViewById(R.id.activitySearchResultLayout));
    }

    private void initSearchResults() {
        initRecyclerView(performSearch());
    }

    private void initRecyclerView(List<Speaker> searchResults) {
        if (searchResults == null) {
            AwesomeTextView noResultText = (AwesomeTextView) findViewById(R.id.noResultText);
            noResultText.setVisibility(View.VISIBLE);
            return;
        }
        AwesomeTextView noResultText = (AwesomeTextView) findViewById(R.id.noResultText);
        noResultText.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.speakerResultRView);
        SpeakerAdapter adapter = new SpeakerAdapter(this, searchResults,
                DataType.Search);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Speaker> performSearch() {  // Called on search button clicked
        if (searchCriteria == null ||
                searchCriteria.length() < 2 ||
                searchCriteria.length() > 30)
            return null;

        if (realm == null)
            realm = Realm.getDefaultInstance();

        String s1 = "*" + searchCriteria;
        String s2 = searchCriteria + "*";
        String s3 = "*" + searchCriteria + "*";

        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH" + " " + congressType);

        OrderedRealmCollection<Speaker> speakers =
                realm.where(Speaker.class)
                        .equalTo("congress", congressType)
                        .beginGroup()
                        .like("name", s1, Case.INSENSITIVE)
                        .or()
                        .like("name", "*" + s2, Case.INSENSITIVE)
                        .or()
                        .like("name", "*" + s3, Case.INSENSITIVE)
                        .or()
                        .like("affiliation", s1, Case.INSENSITIVE)
                        .or()
                        .like("affiliation", s2, Case.INSENSITIVE)
                        .or()
                        .like("affiliation", s3, Case.INSENSITIVE)
                        .or()
                        .like("country", s1, Case.INSENSITIVE)
                        .or()
                        .like("country", s2, Case.INSENSITIVE)
                        .or()
                        .like("country", s3, Case.INSENSITIVE)
                        .or()
                        .like("topic", s1, Case.INSENSITIVE)
                        .or()
                        .like("topic", s2, Case.INSENSITIVE)
                        .or()
                        .like("topic", s3, Case.INSENSITIVE)
                        .or()
                        .like("aabstract.keyword", s1, Case.INSENSITIVE)
                        .or()
                        .like("aabstract.keyword", s2, Case.INSENSITIVE)
                        .or()
                        .like("aabstract.keyword", s3, Case.INSENSITIVE)
                        .endGroup()
                        .findAllSorted("type");

        if (speakers == null ||
                speakers.size() == 0)
            return null;

        List<Speaker> searchResults = new ArrayList<>();
        for (Speaker s : speakers) {
            System.out.println(s.congress + " " +
                    s.type + " " + s.id + " " + s.name);
            searchResults.add(s);
        }

        return searchResults;
    }
}
